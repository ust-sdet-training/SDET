import {expect, test as base} from '@playwright/test';
import crypto from "node:crypto";
import {type AppLogger, logger, redactForLog} from '../utils/logWorks';

type DiagnosticLogFixtures = {
    correlationId: string;
    log: AppLogger;
};

export const test = base.extend<DiagnosticLogFixtures>({
    correlationId: async ({}, use) => {
        await use(crypto.randomUUID());
    },

    log: async ({page, correlationId}, use, testInfo) => {
        await page.setExtraHTTPHeaders({
            "x-correlation-id": correlationId
        });
        
        const baseLog = {
            correlationId,
            project: testInfo.project.name,
            service: "sdet-retail-playwright",
            specFile: testInfo.file,
            testId: testInfo.title,
            workerIndex: testInfo.workerIndex
        };
        const log = logger.child(baseLog);
        const lines: string[] = [];
        const diagnosticLog = log as AppLogger & Record<string, unknown>;

        for (const level of ["error", "warn", "info", "http", "debug"] as const) {
            const original = log[level].bind(log);
            diagnosticLog[level] = (message: string, meta: Record<string, unknown> = {}) => {
                if (log.isLevelEnabled(level)) {
                    lines.push(
                        JSON.stringify(
                            redactForLog({
                                ...baseLog,
                                ...meta,
                                level,
                                message,
                                timestamp: new Date().toISOString()
                            })
                        )
                    );
                }
                original(message, meta);
                return diagnosticLog as any;
            };
    }

    diagnosticLog.info("test started");

    await use(diagnosticLog as AppLogger);

    diagnosticLog.info("test finished", {status : testInfo.status ?? "unknown"});
    if(testInfo.status !== testInfo.expectedStatus && lines.length > 0){
        await testInfo.attach("logs.ndjson", {
            body: lines.join("\n"),
            contentType: "application/x-ndjson"
        });
    }
}
});

export {expect};