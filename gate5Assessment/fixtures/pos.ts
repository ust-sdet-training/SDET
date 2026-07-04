import { test as base, expect } from '@playwright/test';
import crypto from 'node:crypto';
import { type AppLogger, logger, redactForLog } from '../src/logger';

type Evidence = {
  cartResponse?: unknown;
  requestFailures: string[];
  attempts: number;
  successPosts: number;
  idempotencyKeys: Set<string>;
};

type PosFixtures = {
  correlationId: string;
  log: AppLogger;
  evidence: Evidence;
};

export const test = base.extend<PosFixtures>({
  correlationId: async ({}, use) => {
    await use(crypto.randomUUID());
  },

  evidence: async ({}, use) => {
    const evidence: Evidence = {
      cartResponse: undefined,
      requestFailures: [],
      attempts: 0,
      successPosts: 0,
      idempotencyKeys: new Set<string>(),
    };
    await use(evidence);
  },

  log: async ({ page, correlationId }, use, testInfo) => {
    await page.setExtraHTTPHeaders({ 'x-correlation-id': correlationId });

    const baseMeta = {
      correlationId,
      project: testInfo.project.name,
      service: 'sdet-retail-playwright',
      specFile: testInfo.file,
      testId: testInfo.title,
      workerIndex: testInfo.workerIndex,
    };

    const log = logger.child(baseMeta);
    const lines: string[] = [];
    const diagnosticLog = log as AppLogger & Record<string, unknown>;

    for (const level of ['error', 'warn', 'info', 'http', 'debug'] as const) {
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

    diagnosticLog.info('test started');
    await use(diagnosticLog as AppLogger);
    diagnosticLog.info('test finished', { status: testInfo.status ?? 'unknown' });

    if (testInfo.status !== testInfo.expectedStatus && lines.length > 0) {
      await testInfo.attach('diagnostic-log.json', {
        body: lines.join('\n'),
        contentType: 'application/json',
      });
    }
  },
});

export { expect };