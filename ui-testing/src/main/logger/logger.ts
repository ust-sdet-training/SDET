import winston, { Logger } from "winston";

const sensitiveKeys = new Set([
    "authorization",
    "cardnumber",
    "cvv",
    "password",
    "token"
]);
 
export function redactForLog(value: unknown): unknown {
    if(Array.isArray(value)){
        return value.map(redactForLog);
    }
 
    if(!value || typeof value !== "object"){
        return value;
    }
 
    return Object.fromEntries(
        Object.entries(value).map(([key, fieldValue]) => [
            key,
            sensitiveKeys.has(key.toLowerCase()) ? "[REDACTED]" : redactForLog(fieldValue)
        ])
    );
}
 
const redactSensitiveFields = winston.format((info) => {
    for(const [key, value] of Object.entries(info)) {
        if(sensitiveKeys.has(key.toLowerCase())) {
            info[key] = "[REDACTED]";
        } else {
            info[key] = redactForLog(value);
        }
    }
    return info;
})
 
const { combine, errors, json, timestamp} = winston.format;
 
export const logger = winston.createLogger({
    level: process.env.LOG_LEVEL ?? "info",
    format: combine(redactSensitiveFields(), timestamp(), errors({ stack: true}), json()),
    defaultMeta: {
        service: "sdet-retail-playwright"
    },
    transports: [new winston.transports.Console()]
});
 
export type AppLogger = typeof logger;