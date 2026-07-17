import winston from 'winston';

export class Logger {

    private static logger = winston.createLogger({

        level: 'info',

        format: winston.format.combine(

            winston.format.timestamp({

                format: 'DD-MM-YYYY HH:mm:ss'

            }),

            winston.format.printf(({ timestamp, level, message }) => {

                return `${timestamp} [${level.toUpperCase()}] ${message}`;

            })

        ),

        transports: [

            new winston.transports.Console(),

            new winston.transports.File({

                filename: 'evidence/logs/execution.log'

            })

        ]

    });

    static info(message: string) {

        this.logger.info(message);

    }

    static error(message: string) {

        this.logger.error(message);

    }

    static warn(message: string) {

        this.logger.warn(message);

    }

    static success(message: string) {

        this.logger.info(`✅ ${message}`);

    }

}