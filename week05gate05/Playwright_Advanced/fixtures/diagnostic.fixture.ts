import { expect, test as base } from "@playwright/test";
import crypto from "node:crypto";
import {
  type AppLogger,
  logger,
  redactForLog,
} from "../src/logging/logger";
// custom fixture type
type DiagnosticFixtures = {
  correlationId: string;
  log: AppLogger;
};
// Generate a unique Correlation ID for every test execution
export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },

  log: async ({ page, correlationId }, use, testInfo) => {
     // Add Correlation ID to every HTTP request sent by the browser
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

    const diagnosticLog = log as AppLogger & Record<string, any>;
// Override all logging levels
    for (const level of [
      "error",
      "warn",
      "info",
      "http",
      "debug",
    ] as const) {
      const original = log[level].bind(log);
// Capture every log before printing it
      (diagnosticLog as any)[level] = (
        message: string,
        meta: Record<string, unknown> = {}
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
              })
            )
          );
        }

        return original(message, meta);
      };
    }

    diagnosticLog.info("test started");

    await use(diagnosticLog);

    diagnosticLog.info("test finished", {
      status: testInfo.status ?? "unknown",
    });
// Attach logs only if the test result is different from the expected result
    if (
      testInfo.status !== testInfo.expectedStatus &&
      lines.length > 0
    ) {
      // Attach collected logs to the Playwright HTML report
      await testInfo.attach("logs.ndjson", {
        body: lines.join("\n"),
        contentType: "application/x-ndjson",
      });
    }
  },
});

export { expect };