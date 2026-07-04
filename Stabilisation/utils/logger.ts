import winston from "winston";

// Fields that should not be logged
const sensitiveKeys = new Set([
  "authorization",
  "password",
  "token",
  "cardnumber",
  "cvv",
]);

// Replacing sensitive values with [REDACTED]
function redact(value: unknown): unknown {
  if (Array.isArray(value)) {
    return value.map(redact);
  }

  if (!value || typeof value !== "object") {
    return value;
  }

  return Object.fromEntries(
    Object.entries(value).map(([key, fieldValue]) => [
      key,
      sensitiveKeys.has(key.toLowerCase())
        ? "[REDACTED]"
        : redact(fieldValue),
    ])
  );
}

const redactSensitiveFields = winston.format((info) => {
  for (const [key, value] of Object.entries(info)) {
    info[key] = sensitiveKeys.has(key.toLowerCase())
      ? "[REDACTED]"
      : redact(value);
  }

  return info;
});

const simpleFormat = winston.format.printf(({ timestamp, level, message, ...meta }) => {
  const details = Object.entries(meta)
    .filter(([, value]) => value !== undefined && value !== null)
    .map(([key, value]) => {
      if (typeof value === "object") {
        return `${key}=${JSON.stringify(value)}`;
      }

      return `${key}=${value}`;
    })
    .join(" ");

  return [timestamp, `[${level}]`, message, details].filter(Boolean).join(" ");
});

export const logger = winston.createLogger({
  level: process.env.LOG_LEVEL ?? "info",

  format: winston.format.combine(
    redactSensitiveFields(),
    simpleFormat
  ),

  transports: [
    new winston.transports.Console(),

    new winston.transports.File({
      filename: "test-results/logs/refund-tests.log",
    }),
  ],
});