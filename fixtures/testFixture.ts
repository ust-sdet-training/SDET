import { test as base, expect  } from '@playwright/test';
import { logger } from '../utils/logger';
import crypto from 'crypto';

export const test = base.extend<{ log: typeof logger }>({
  log: async ({ page }, use, testInfo) => {
    const correlationId = crypto.randomUUID();

    const log = logger.child({
      correlationId,
      testName: testInfo.title,
    });

    const logs: string[] = [];

    // Capture the important log events in a form that can be attached to the test output.
    for (const level of ["error", "warn", "info", "debug", "http"] as const) {
      const originalLevelMethod = (
        log[level] as unknown as (message: string, meta?: Record<string, unknown>) => unknown
      ).bind(log);

      (log[level] as unknown as (message: string, meta?: Record<string, unknown>) => unknown) = (
        (message: string, meta: Record<string, unknown> = {}) => {
          logs.push(JSON.stringify({ level, message, meta, correlationId }));
          return originalLevelMethod(message, meta);
        }
      ) as any;
    }

    log.info("test started", { testName: testInfo.title });
    await use(log);
    log.info("test finished", { status: testInfo.status ?? "unknown" });

    // Attach a concise summary alongside the detailed log stream so the report feels more useful.

    const summary = {
      testName: testInfo.title,
      workerIndex: testInfo.workerIndex,
      status: testInfo.status ?? "unknown",
      expectedStatus: testInfo.expectedStatus,
      correlationId,
      logCount: logs.length,
    };

    await testInfo.attach("test-summary.json", {
      body: JSON.stringify(summary, null, 2),
      contentType: "application/json",
    });

    if (logs.length > 0) {
      await testInfo.attach("logs.ndjson", {
        body: logs.join("\n"),
        contentType: "application/x-ndjson",
      });
    }
  },
});

export { expect };