import winston from "winston";
// Keep common secrets out of the logs so the output stays safe to share.
const sensitiveKeys = new Set([
   "authorization",
   "cardnumber",
   "cvv",
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
// Redact sensitive values before they ever reach the logger output.
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
const { combine, errors, timestamp } = winston.format;

// This logger keeps the test output readable while still carrying useful context.
export const logger = winston.createLogger({
   level: process.env.LOG_LEVEL ?? "info",
   format: combine(
       redactSensitiveFields(),
       timestamp(),
       errors({ stack: true }),
       winston.format.printf(({ level, message, timestamp, ...meta }) => {
           const detailEntries = Object.entries(meta)
               .filter(([key]) => !["level", "message", "timestamp"].includes(key));
           const detailText = detailEntries.length
               ? ` ${JSON.stringify(Object.fromEntries(detailEntries))}`
               : "";
           return `[${level.toUpperCase()}] ${timestamp} - ${message}${detailText}`;
       })
   ),
   defaultMeta: {
       service: "sdet-retail-playwright"
   },
   transports: [new winston.transports.Console()]
});

export type AppLogger = typeof logger;

export async function evidenceLog(
  testInfo: { attach: (name: string, options: { body: string; contentType: string }) => Promise<unknown> },
  label: string,
  lines: string[]
) {
  await testInfo.attach(`${label}.txt`, {
    body: lines.join("\n"),
    contentType: "text/plain"
  });
}