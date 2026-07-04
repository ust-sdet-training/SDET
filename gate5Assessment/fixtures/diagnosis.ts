import { test as base, expect } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../src/logger";

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


    if (lines.length > 0) {
      const rows = lines.map((line, i) => {
        const parsed = JSON.parse(line);
        const time = new Date(parsed.timeStamp).toISOString().split("T")[1].replace("Z", "");
        return { Step: i + 1, Timestamp: time, Message: parsed.message };
      });

      console.log(`\nExecution Timeline — ${testInfo.title}`);
      console.log(`Status: ${(testInfo.status ?? "unknown").toUpperCase()}\n`);
      console.table(rows);
    }

    if (testInfo.status !== testInfo.expectedStatus && lines.length > 0) {
      await testInfo.attach("diagnostic-log.json", {
        body: lines.join("\n"),
        contentType: "application/json",
      });
    }
  },
});

export { expect };