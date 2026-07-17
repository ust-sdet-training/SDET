import winston from "winston";
 
const { combine, timestamp, printf } = winston.format;
 
const logFormat = printf((info) => {const { timestamp, level, message, ...meta } = info;
   const filteredMeta = Object.fromEntries(

   Object.entries(meta).filter(([_, v]) => v !== undefined && v !== null));
      return `[${level.toUpperCase()} - ${timestamp}] ${message}${
    Object.keys(filteredMeta).length? ` ${JSON.stringify(filteredMeta)}`: ""}`;})
 
const redactFormat = winston.format((info) => {
  return redactSensitiveFields(info);
});

export const logger = winston.createLogger({
  level: "info",
  format: combine(
    redactFormat(),
    timestamp(),
    logFormat
  ),
  transports: [new winston.transports.Console()],
});

export function  redactSensitiveFields(obj: any): any {
  return JSON.parse(
    JSON.stringify(obj, (key, value) => {
      if (
        key.toLowerCase().includes("password") ||
        key.toLowerCase().includes("token") ||
        key.toLowerCase().includes("secret") ||
        key.toLowerCase().includes("cardname") ||
        key.toLowerCase().includes("cardnumber") ||
        key.toLowerCase().includes("cvv")
      ) {
        return "***REDACTED***";
      }
      return value;
    })
  );
}
 
export type AppLogger = typeof logger;