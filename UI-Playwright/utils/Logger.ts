import winston from "winston";

export const Logger = winston.createLogger({
    level: "info",

    format: winston.format.combine(
        winston.format.timestamp({
            format: "YYYY-MM-DD HH:mm:ss",
        }),

        winston.format.printf(({ timestamp, level, message }) => {
            return `${timestamp} [${level.toUpperCase()}] ${message}`;
        })
    ),

    transports: [

        // Console logs
        new winston.transports.Console(),

        // File logs
        new winston.transports.File({
            filename: "test-results/logs/framework.log",
        }),
    ],
});