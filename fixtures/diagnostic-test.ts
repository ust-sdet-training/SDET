import { expect, test as base } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../pages/logger";

type DiagnosticFixtures = {
  correlationId: string;
  log: AppLogger;
};

const LOG_LEVELS = ["error", "warn", "info", "http", "debug"] as const;

export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },

  log: async ({ page, correlationId }, use, testInfo) => {
    await page.setExtraHTTPHeaders({ "x-correlation-id": correlationId });

    const baseMeta = {
      correlationId,
      project: testInfo.project.name,
      specFile: testInfo.file,
      testId: testInfo.title,
      workerIndex: testInfo.workerIndex
    };

    const scopedLogger = logger.child(baseMeta);
    const capturedLines: string[] = [];
    const wrappedLogger = scopedLogger as AppLogger & Record<string, unknown>;

    LOG_LEVELS.forEach((level) => {
      const originalMethod = scopedLogger[level].bind(scopedLogger);

      wrappedLogger[level] = (message: string, meta: Record<string, unknown> = {}) => {
        if (scopedLogger.isLevelEnabled(level)) {
          const entry = redactForLog({
            ...baseMeta,
            ...meta,
            level,
            message,
            timestamp: new Date().toISOString()
          });
          capturedLines.push(JSON.stringify(entry));
        }
        originalMethod(message, meta);
        return wrappedLogger;
      };
    });

    wrappedLogger.info("test started");
    await use(wrappedLogger as AppLogger);
    wrappedLogger.info("test finished", { status: testInfo.status ?? "unknown" });

    const testFailed = testInfo.status !== testInfo.expectedStatus;
    if (testFailed && capturedLines.length > 0) {
      await testInfo.attach("diagnostic-log.ndjson", {
        body: capturedLines.join("\n"),
        contentType: "application/x-ndjson"
      });
    }
  }
});

export { expect } from "@playwright/test";