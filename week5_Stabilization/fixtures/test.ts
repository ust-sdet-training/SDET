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

    const log = logger.child(baseMeta);
    const lines: string[] = [];
    const diagnosticLog = log as AppLogger & Record<string, unknown>;

    for (const level of ["error", "warn", "info", "http", "debug"] as const) {
      const original = log[level].bind(log);
      diagnosticLog[level] = (message: string, meta: Record<string, unknown> = {}) => {
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
        return diagnosticLog;
      };
    }

    diagnosticLog.info("test started");
    await use(diagnosticLog as AppLogger);
    diagnosticLog.info("test finished", { status: testInfo.status ?? "unknown" });

    if (testInfo.status !== testInfo.expectedStatus && lines.length > 0) {
      await testInfo.attach("diagnostic-log.ndjson", {
        body: lines.join("\n"),
        contentType: "application/x-ndjson"
      });
    }
  },

  evidence: async ({}, use, testInfo) => {
    await use({
      attachJson: async (name, body) => {
        await testInfo.attach(name, {
          body: JSON.stringify(body, null, 2),
          contentType: "application/json"
        });
      },
      attachText: async (name, body) => {
        await testInfo.attach(name, {
          body,
          contentType: "text/plain"
        });
      }
    });
  }
});

export { expect } from "@playwright/test";
