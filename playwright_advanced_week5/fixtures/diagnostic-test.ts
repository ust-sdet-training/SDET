import {expect, test as base} from '@playwright/test';
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from '../src/logger';
import { spec } from 'node:test/reporters';
 
type DiagnosticFixtures = {
    correlationId: string;
    log:AppLogger;
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
        const lines:string[] = [];
        const diaganosticLog = log as AppLogger & Record<string, unknown>;
 
        for(const level of ["debug", "info", "warn", "error", "http"] as const){
            const original = log[level].bind(log);
            diaganosticLog[level] = (message:string, meta:Record<string, unknown> = {}) => {
                if(log.isLevelEnabled(level)){
                    lines.push(JSON.stringify(redactForLog({...baseMeta, ...meta, level, message, timestamp: new Date().toISOString()})));
                }
                original(message, meta);
                return diaganosticLog;
            };
        }
 
        diaganosticLog.info("test started");
        await use(diaganosticLog as AppLogger);
        diaganosticLog.info("test finished", {status: testInfo.status ?? "unknown"});
 
        if (lines.length > 0) {
        await testInfo.attach("log.ndjson", {
        body: lines.join("\n"),
        contentType: "application/json"
    });
}
        
    }
});
 
export {expect};