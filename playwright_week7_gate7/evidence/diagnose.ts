import { expect, test as base } from "@playwright/test";
import crypto from "node:crypto";
import {
  type AppLogger,
  logger,
  redactForLog,
} from "../src/logger/Logger"

type DiagnosticFixtures = {
  correlationId: string;
  log: AppLogger;
};

export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },

  log: async ({ page, correlationId }, use, testInfo) => {
    await page.setExtraHTTPHeaders({
      "x-correlation-id": correlationId,
    });

    const baseMeta = {
      correlationId,
      project: testInfo.project.name,
      service: "shopkart",
      specFile: testInfo.file,
      testId: testInfo.title,
      workerIndex: testInfo.workerIndex,
    };

    const log = logger.child(baseMeta);

    const lines: string[] = [];

    const diagnosticLog = log as AppLogger & Record<string, any>;

    for (const level of [
      "error",
      "warn",
      "info",
      "http",
      "debug",
    ] as const) {
      const original = log[level].bind(log);

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

    if (
    //   testInfo.status !== testInfo.expectedStatus &&
      lines.length > 0

    ) {
      await testInfo.attach("logs.json", 
        {
        body: JSON.stringify(
            lines.map((line) => JSON.parse(line)),
            null,
            2
        ),
        contentType: "application/json",
        }
);
    }
  },
});

export { expect };