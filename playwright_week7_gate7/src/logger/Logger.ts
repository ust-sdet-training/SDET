import winston from "winston";

const sensitiveKeys = new Set([
    "authorization",
    "cardnumber",
    "cvv",
    "password",
    "token"
])

export function redactForLog(value: unknown): unknown{
    if(Array.isArray(value)){
        return value.map(redactForLog)
    }
    if(!value || typeof value !== "object"){
        return value;
    }
    return Object.fromEntries(Object.entries(value).map(([key,fieldValue])=>[
        key,
        sensitiveKeys.has(key.toLowerCase())? "[REDACTED]": redactForLog(fieldValue)
    ])
)
}

const redactSensitiveFields = winston.format((info)=>{
    for (const [key,value] of Object.entries(info)){
        if(sensitiveKeys.has(key.toLowerCase())){
            info[key] = "[REDACTED]"
        }
        else{
            info[key] = redactForLog(value)
        }
    }
    return info
})

const {combine,errors , json, printf,timestamp, colorize} = winston.format

const terminalFormat = printf((info) => {
    const { level, message, correlationId } = info;

    const shortId = correlationId ? String(correlationId).substring(0, 8) : "NO-ID";

    return `[${shortId}] ${level.toUpperCase()}: ${message}`;
});

export const logger = winston.createLogger({
    level: process.env.LOG_LEVEL ?? "info",
    format: combine(redactSensitiveFields(),timestamp(),errors({stack:true}),json()),
    defaultMeta:{
        service: "shopkart"
    },
   transports: [
        new winston.transports.Console({
            format: combine(
                colorize(),
                terminalFormat
            )
        })
   ]
})

export type AppLogger = typeof logger