import winston from "winston";

// List of sensitive fields that should never appear in logs
const sensitiveKeys = new Set([
  "authorization",
  "cardnumber",
  "cvv",
  "password",
  "token"
]);

// Masking sensitive data before logging
export function redactForLog(value: unknown): unknown {
  if (Array.isArray(value)) {
    return value.map(redactForLog);
  }

  if (!value || typeof value !== "object") {
    return value;
  }

  return Object.fromEntries(
    Object.entries(value).map(([key, fieldValue]) => [
      key,
      sensitiveKeys.has(key.toLowerCase())
        ? "[REDACTED]"
        : redactForLog(fieldValue)
    ])
  );
}

// Winston formatter to automatically redact sensitive fields in every log
const redactSensitiveFields = winston.format((info) => {
  for (const [key, value] of Object.entries(info)) {
    if (sensitiveKeys.has(key.toLowerCase())) {
      info[key] = "[REDACTED]";
    } else {
      info[key] = redactForLog(value);
    }
  }

  return info;
})();

const { combine, errors, timestamp, printf } = winston.format;

// Create a centralized application logger
export const logger = winston.createLogger({
  level: process.env.LOG_LEVEL ?? "info",
  format: combine(
    redactSensitiveFields,
    timestamp(),
    errors({ stack: true }),
    // Define how each log message should be displayed
    printf(({ level, message, timestamp, ...meta }) => {
      const ignored = [
        "service",
        "correlationId",
        "project",
        "specFile",
        "testId",
        "workerIndex"
      ];

      for (const key of ignored) {
        delete meta[key];
      }

      const metadata =
        Object.keys(meta).length > 0
          ? ` ${JSON.stringify(meta)}`
          : "";

      return `[${level.toUpperCase()}] ${timestamp} - ${message}${metadata}`;
    })
  ),
   // Default metadata added to every log
  defaultMeta: {
    service: "sdet-retail-playwright"
  },
   // Output logs to the console
  transports: [new winston.transports.Console()]
});

// Export logger type for use in fixtures and tests
export type AppLogger = typeof logger;