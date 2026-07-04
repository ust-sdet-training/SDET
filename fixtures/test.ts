import { test as base, expect } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../pages/logger";

type Evidence = {
  attachJson: (name: string, body: unknown) => Promise<void>;
  attachText: (name: string, body: string) => Promise<void>;
};

type Fixtures = {
  correlationId: string;
  log: AppLogger;
  evidence: Evidence;
};

const LOG_LEVELS = ["error", "warn", "info", "http", "debug"] as const;

export const test = base.extend<Fixtures>({
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
  },

  evidence: async ({}, use, testInfo) => {
    const attach = (name: string, body: string, contentType: string) =>
      testInfo.attach(name, { body, contentType });

    await use({
      attachJson: (name, body) => attach(name, JSON.stringify(body, null, 2), "application/json"),
      attachText: (name, body) => attach(name, body, "text/plain")
    });
  }
});

export { expect } from "@playwright/test";  