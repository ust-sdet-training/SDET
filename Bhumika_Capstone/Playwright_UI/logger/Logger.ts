import fs from "fs";
import path from "path";
import winston from "winston";

export const logger = winston.createLogger({
  level: "info",

  format: winston.format.combine(
    winston.format.timestamp({
      format: "YYYY-MM-DD HH:mm:ss",
    }),

    winston.format.errors({ stack: true }),

    winston.format.printf(({ timestamp, level, message }) => {
      return `${timestamp} [${level.toUpperCase()}] ${message}`;
    }),
  ),

  transports: [
    new winston.transports.Console(),

    new winston.transports.File({
      filename: "logs/framework.log",
    }),
  ],
});

// ensure logs directory exists
try {
  const logsDir = path.resolve(process.cwd(), "logs");
  if (!fs.existsSync(logsDir)) fs.mkdirSync(logsDir, { recursive: true });
} catch (e) {
  // ignore directory creation errors
}
