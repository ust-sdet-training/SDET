import {expect, test as base} from '@playwright/test';
import crypto from "node:crypto";
import {type AppLogger, logger, redactForLog} from '../utils/logger';

type DiagnosticFixtures = {
    correlationId: string;
    log: AppLogger;
};

export const test = base.extend<DiagnosticFixtures>({
    correlationId: async ({}, use) => {
        await use(crypto.randomUUID());
    },

    log: async ({page, correlationId}, use, testInfo) => {
        await page.setExtraHTTPHeaders({
            "x-correlation-id": correlationId
        });
        
        const baseMeta = {
            correlationId,
            project: testInfo.project.name,
            service: "sdet-retail-playwright",
            specFile: testInfo.file,
            testId: testInfo.title,
            workerIndex: testInfo.workerIndex
        };
        const log = logger.child(baseMeta);
        const lines: string[] = [];
        const diagnosticLog = log as AppLogger & Record<string, unknown>;

        // for (const level of ["error", "warn", "info", "http", "verbose", "debug", "silly"] as const) {
        for (const level of ["error", "warn", "info", "http", "debug"] as const) {
            const original = log[level].bind(log);
            (diagnosticLog as any)[level] = (message: string, meta: Record<string, unknown> = {}) => {
                if (log.isLevelEnabled(level)) {
                    lines.push(
                        JSON.stringify(
                            redactForLog({
                                ...baseMeta,
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
        }
        );
        const snapshot = await page.screenshot({
            mask: [
                page.getByLabel("Password")
            ]
        });
        await testInfo.attach("failure-screenshot.png", {
            body: snapshot,
            contentType: "image/png"
        });

    }
}
});

export {expect};