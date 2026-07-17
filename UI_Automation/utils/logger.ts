import winston from "winston";

const sensitiveKeys = new Set([
    "authorization",
    "cardnumber",
    "cvv",
    "email",
    "password",
    "token"
]);

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
            sensitiveKeys.has(key.toLowerCase()) ? "[REDACTED]" : redactForLog(fieldValue)
        ])
    )
}

const redactSensitiveFields = winston.format((info) => {
    for (const [key,value] of Object.entries(info)) {
        if(sensitiveKeys.has(key.toLowerCase())){
            info[key] = "[REDACTED]";
        } else {
            info[key] = redactForLog(value);
        }
    }
    return info;
});

const { combine, errors, json, timestamp } = winston.format;


winston.addColors({
    error: "red",
    warn: "yellow",
    info: "blue",
    http: "magenta",
    debug: "green"
});

const colorizer = winston.format.colorize();

export const logger = winston.createLogger({
    level: process.env.LOG_LEVEL ?? "info",
    format: combine(redactSensitiveFields(), timestamp(), errors({ stack: true }), 
    // json()),
    winston.format.printf(
        ({ level, correlationId, testId, timestamp, message}) => {
            const colorForLogLevels = colorizer.colorize(level, level.toUpperCase());
            
    return `Timestamp=${timestamp} | Level=[${level.toUpperCase()}] | CID=${correlationId} | Test=${testId} | Message=${message}`;
    })),
    defaultMeta: {
        service: "shopkart-retail-playwright"
    },
    transports: [new winston.transports.Console()]
});

export type AppLogger = typeof logger;