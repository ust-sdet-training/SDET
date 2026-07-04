import { expect, test as base } from '@playwright/test';
import crypto from 'node:crypto';
import { type Applogger, logger, redactForLog } from '../src/logger';

type DiagnosticFixtures = {
  correlationId: string;
  log: Applogger;
};

export const test = base.extend<DiagnosticFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },

  log: async ({ page, correlationId }, use, testInfo) => {
    await page.setExtraHTTPHeaders({
      'x-correlation-id': correlationId,
    });

    const baseMeta = {
      correlationId,
      testTitle: testInfo.project.name,
      service: 'sdet-retail-playwright',
      specFile: testInfo.file,
      testId: testInfo.title,
      workerIndex: testInfo.workerIndex,
    };

    const log = logger.child(baseMeta);
    const lines: string[] = [];
    const diagnosticLog = log as Applogger & Record<string, unknown>;

    for (const level of ['error', 'warn', 'info', 'http', 'debug'] as const) {
      const originalMethod = (log as unknown as { [key: string]: unknown })[level] as
        | ((message: string, meta?: Record<string, unknown>) => unknown)
        | undefined;

      diagnosticLog[level] = ((message: string, meta: Record<string, unknown> = {}) => {
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

        originalMethod?.(message, meta);
        return diagnosticLog;
      }) as unknown as Applogger[keyof Applogger];
    }

    diagnosticLog.info('test started');

    await use(diagnosticLog as Applogger);

    diagnosticLog.info('test finished', { status: testInfo.status ?? 'unknown' });

    if (testInfo.status !== testInfo.expectedStatus && lines.length > 0) {
      await testInfo.attach('log.ndjson', {
        body: lines.join('\n'),
        contentType: 'application/json',
      });
    }
  },
});

export { expect };
