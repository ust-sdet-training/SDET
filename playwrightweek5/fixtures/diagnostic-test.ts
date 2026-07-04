import { expect, test as base } from "@playwright/test";
import crypto from "node:crypto";
import { type AppLogger, logger, redactForLog } from "../src/logger";

type DiagnosticFixtures = {
  correlationId: string;
  log: AppLogger;
};

export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    // Unique ID helps correlate logs across services.
    await use(crypto.randomUUID());
  },

  log: async ({ page, correlationId }, use, testInfo) => {
    // Attach correlation ID to every outgoing request.
    await page.setExtraHTTPHeaders({
      "x-correlation-id": correlationId,
    });

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

    // Capture logs and store them as an artifact.
    for (const level of ["debug", "info", "warn", "error", "http"] as const) {
      const original = log[level].bind(log);

      diagnosticLog[level] = (
        message: string,
        meta: Record<string, unknown> = {},
      ) => {
        if (log.isLevelEnabled(level)) {
          lines.push(
            JSON.stringify(
              redactForLog({
                ...baseMeta,
                ...meta,
                level,
                message,
                timestamp: new Date().toISOString(),
              }),
            ),
          );
        }

        original(message, meta);
        return diagnosticLog;
      };
    }

    diagnosticLog.info("test started");

    await use(diagnosticLog as AppLogger);

    diagnosticLog.info("test finished", {
      status: testInfo.status ?? "unknown",
    });

    // Attach collected logs to the Playwright report.
    if (lines.length > 0) {
      await testInfo.attach("log.ndjson", {
        body: lines.join("\n"),
        contentType: "application/json",
      });
    }
  },
});
export { expect };
