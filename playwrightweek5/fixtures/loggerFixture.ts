import { expect, test as base } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../src/logger/logger";
import fs from "fs";
 
type LogFixtures = {
    correlationId: string;
    log: AppLogger;
};
 
export const test = base.extend<LogFixtures>({
    correlationId: async ({}, use) => {
        await use(crypto.randomUUID());
    },
 
    log: async ({page, correlationId}, use, testInfo) => {
        await page.setExtraHTTPHeaders({"x-correlation-id": correlationId});
 
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
 
        for(const level of ["error","warn","info","http","debug"] as const) {
            const original = log[level].bind(log);
            diagnosticLog[level] = (...args: any[]) => {
                const [message, meta = {}] = args;

                if (log.isLevelEnabled(level)) {
                    lines.push(
                        JSON.stringify(
                            redactForLog({
                                ...baseMeta,
                                ...(typeof meta === "object" ? meta : {}),
                                level,
                                message,
                                timestamp: new Date().toISOString()
                            })
                        )
                    );
                }
                original(message, meta);
                return diagnosticLog;
            };
             
        }
        
        await use(diagnosticLog)
        
        

        if (testInfo.status !== testInfo.expectedStatus){
            const logFile = testInfo.outputPath("execution.log");

        fs.writeFileSync(logFile, lines.join("\n"));
        await testInfo.attach("execution-log", {
        path: logFile,
        contentType: "application/json",
        });
        }
        
    }
    
})

export {expect};