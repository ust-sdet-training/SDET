import { createLogger, format, transports, type Logger } from 'winston';
import type { TestInfo } from '@playwright/test';
import { existsSync, mkdirSync, readFileSync } from 'node:fs';
import path from 'node:path';

const baseLogger: Logger = createLogger({
  level: process.env.LOG_LEVEL ?? 'info',
  defaultMeta: {
    service: 'TripStackTests',
    environment: process.env.NODE_ENV ?? 'test',
  },
  format: format.combine(
    format.timestamp(),
    format.errors({ stack: true }),
    format.splat(),
    format.json()
  ),
  transports: [
    new transports.Console({
      format: format.combine(
        format.colorize(),
        format.timestamp({ format: 'YYYY-MM-DD HH:mm:ss.SSS' }),
        format.printf(({ timestamp, level, message, ...meta }) => {
          const metadata = Object.keys(meta).length ? ` ${JSON.stringify(meta)}` : '';
          return `${timestamp} ${level}: ${message}${metadata}`;
        })
      ),
    }),
  ],
});

export interface TestLoggerData {
  logger: Logger;
  logFile: string;
  correlationId: string;
}

function sanitizeFileName(value: string): string {
  return value
    .replace(/[^a-z0-9-_]+/gi, '_')
    .replace(/__+/g, '_')
    .replace(/^_+|_+$/g, '')
    .slice(0, 120);
}

export function createTestLogger(testInfo: TestInfo): TestLoggerData {
  const correlationId = `${testInfo.project.name}-${testInfo.workerIndex}-${Date.now().toString(36)}-${Math.random().toString(36).slice(2, 8)}`;
  const safeTitle = sanitizeFileName(testInfo.title);
  const logsDir = path.join(process.cwd(), 'test-results', 'logs');
  mkdirSync(logsDir, { recursive: true });
  const logFile = path.join(logsDir, `${testInfo.project.name}-${safeTitle}-${testInfo.workerIndex}-${Date.now()}.log`);

  const childLogger = baseLogger.child({
    testTitle: testInfo.title,
    correlationId,
    project: testInfo.project.name,
  });

  childLogger.add(
    new transports.File({
      filename: logFile,
      format: format.combine(format.timestamp(), format.json()),
      level: 'debug',
    })
  );

  childLogger.info('Per-test logger created', {
    correlationId,
    testTitle: testInfo.title,
  });

  return {
    logger: childLogger,
    logFile,
    correlationId,
  };
}

export async function attachFailureLog(testInfo: TestInfo, logFile: string) {
  // Attach logs only when the observed status differs from the expected status.
  // This avoids attaching logs for intentionally failing tests (expectedStatus).
  const expected = (testInfo as any).expectedStatus ?? 'passed';
  if (testInfo.status !== expected && existsSync(logFile)) {
    const body = readFileSync(logFile);
    await testInfo.attach('diagnostic-log', {
      body,
      contentType: 'text/plain',
    });
  }
}
