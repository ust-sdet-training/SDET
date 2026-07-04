  import winston from "winston";

  const SENSITIVE_FIELD_NAMES = [
    "password",
    "token",
    "cvv",
    "cardnumber",
    "authorization"
  ];

  function isSensitiveField(key: string): boolean {
    return SENSITIVE_FIELD_NAMES.includes(key.toLowerCase());
  }

  function redactForLog(value: unknown): unknown {
    if (Array.isArray(value)) {
      return value.map((item) => redactForLog(item));
    }

    if (value === null || typeof value !== "object") {
      return value;
    }

    const sanitized: Record<string, unknown> = {};
    for (const [key, fieldValue] of Object.entries(value)) {
      sanitized[key] = isSensitiveField(key) ? "[REDACTED]" : redactForLog(fieldValue);
    }
    return sanitized;
  }

  const redactSensitiveFields = winston.format((info) => {
    for (const [key, value] of Object.entries(info)) {
      info[key] = isSensitiveField(key) ? "[REDACTED]" : redactForLog(value);
    }
    return info;
  });

  const { combine, errors, json, timestamp } = winston.format;

  export type AppLogger = winston.Logger;
  export { redactForLog };

  export const logger = winston.createLogger({
    level: process.env.LOG_LEVEL || "info",
    format: combine(redactSensitiveFields(), timestamp(), errors({ stack: true }), json()),
    defaultMeta: { service: "week5-stabilization" },
    transports: [new winston.transports.Console()]
  });