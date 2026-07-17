import fs from "node:fs";
import path from "node:path";
import winston from "winston";

const sensitiveKeys = new Set([
  "authorization",
  "cardnumber",
  "cvv",
  "password",
  "token"
]);

const logDirectory = path.join(process.cwd(), "logs");

if (!fs.existsSync(logDirectory)) {
  fs.mkdirSync(logDirectory, { recursive: true });
}

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

const { combine, timestamp, printf, errors } = winston.format;

export const logger = winston.createLogger({
  level: process.env.LOG_LEVEL ?? "info",

  format: combine(
    redactSensitiveFields,
    timestamp({
      format: "YYYY-MM-DD HH:mm:ss"
    }),
    errors({ stack: true }),
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

  defaultMeta: {
    service: "booking-playwright"
  },

  transports: [
    new winston.transports.Console(),

    new winston.transports.File({
      filename: path.join(logDirectory, "execution.log")
    })
  ]
});

export type AppLogger = typeof logger;