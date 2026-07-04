import { test as base, expect } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../utils/logger";
 
type DiagnosticFixtures = {
  correlationId: string;
  log: AppLogger;
};
 
export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },
 
  log: async ({ page, correlationId }, use, testInfo) => {
    await page.setExtraHTTPHeaders({ "x-correlation-id": correlationId });
 
    const baseMeta = {
      correlationId,
      project: testInfo.project.name,
      service: "sdet-retail-playwright",
      specFile: testInfo.file,
      testId: testInfo.title,
      workerIndex: testInfo.workerIndex,
    };
 
    const log = logger.child(baseMeta);
    const lines: string[] = [];
    const diagnosticLog = log as AppLogger & Record<string, unknown>;

    // Keep a running record of what happened during the test so it can be attached as evidence later.
    for (const level of ["error", "warn", "info", "http", "debug"] as const) {
      const originalLevelMethod = (
        log[level] as unknown as (message: string, meta?: Record<string, unknown>) => unknown
      ).bind(log);
 
      diagnosticLog[level] = ((message: string, meta: Record<string, unknown> = {}) => {
        if (log.isLevelEnabled(level)) {
          lines.push(
            JSON.stringify(
              redactForLog({
                ...baseMeta,
                ...meta,
                level,
                message,
                timeStamp: new Date().toISOString(),
              })
            )
          );
          originalLevelMethod(message, meta);
        }
        return diagnosticLog;
      }) as any;
    }
 
    diagnosticLog.info("test started");
    await use(diagnosticLog as AppLogger);
    diagnosticLog.info("test finished", { status: testInfo.status ?? "unknown" });

    // A small summary makes the artifact easier to read when you open it from the test report.

    const summary = {
      testName: testInfo.title,
      workerIndex: testInfo.workerIndex,
      status: testInfo.status ?? "unknown",
      expectedStatus: testInfo.expectedStatus,
      logCount: lines.length,
      correlationId,
    };

    await testInfo.attach("diagnostic-summary.json", {
      body: JSON.stringify(summary, null, 2),
      contentType: "application/json",
    });

    if (lines.length > 0) {
      await testInfo.attach("diagnostic-log.ndjson", {
        body: lines.join("\n"),
        contentType: "application/x-ndjson",
      });
    }
  },
});
 
export { expect };
 
 