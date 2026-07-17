import { randomUUID } from 'node:crypto';
import type { TestInfo } from '@playwright/test';
import { maskSensitivePayload, maskValue } from './maskUtil';

type LoggerContext = Record<string, string | number | boolean | undefined>;

const getSecretValues = (): string[] => {
  return Object.entries(process.env)
    .filter(([key, value]) => Boolean(value) && /(?:PASSWORD|PAYMENT|PASSENGER|TRIPSTACK_EMAIL)/.test(key))
    .map(([, value]) => value as string);
};

export type TestLogger = {
  correlationId: string;
  entries: () => string;
  info: (message: string, context?: LoggerContext) => void;
  warn: (message: string, context?: LoggerContext) => void;
  error: (message: string, context?: LoggerContext) => void;
  step: (message: string, context?: LoggerContext) => void;
  start: (message: string, context?: LoggerContext) => void;
  complete: (message: string, context?: LoggerContext) => void;
  tracePayload: (label: string, value: string, context?: LoggerContext) => void;
};

const createLogContext = (correlationId: string, context: LoggerContext = {}): LoggerContext => ({
  correlationId,
  timestamp: new Date().toISOString(),
  environment: process.env.NODE_ENV ?? 'local',
  ...context,
});

export const redactMessage = (message: string): string => {
  return getSecretValues().reduce((updatedMessage, secretValue) => updatedMessage.replaceAll(secretValue, maskValue(secretValue)), message);
};

const createLogEntry = (level: 'INFO' | 'WARN' | 'ERROR' | 'TRACE', message: string, correlationId: string, context: LoggerContext = {}) => ({
  level,
  message: redactMessage(message),
  ...createLogContext(correlationId, context),
});

const printLogEntry = (entry: ReturnType<typeof createLogEntry>): void => {
  const logMessage = JSON.stringify(entry);

  switch (entry.level) {
    case 'WARN':
      console.warn(logMessage);
      break;
    case 'ERROR':
      console.error(logMessage);
      break;
    default:
      console.log(logMessage);
  }
};

const writeLog = (level: 'INFO' | 'WARN' | 'ERROR' | 'TRACE', message: string, correlationId: string, context: LoggerContext = {}): void => {
  const entry = createLogEntry(level, message, correlationId, context);
  printLogEntry(entry);
};

export const createTestLogger = (testInfo: TestInfo): TestLogger => {
  const correlationId = `${testInfo.project.name}-${randomUUID().slice(0, 8)}`;
  const logEntries: string[] = [];

  const testContext: LoggerContext = {
    testId: testInfo.testId,
    testTitle: testInfo.title,
    retry: testInfo.retry,
  };

  const log = (level: 'INFO' | 'WARN' | 'ERROR' | 'TRACE', message: string, context: LoggerContext = {}) => {
    const entry = createLogEntry(level, message, correlationId, { ...testContext, ...context });
    logEntries.push(JSON.stringify(entry));
    printLogEntry(entry);
  };

  return {
    correlationId,
    entries: () => `${logEntries.join('\n')}\n`,
    info: (message, context = {}) => log('INFO', message, context),
    warn: (message, context = {}) => log('WARN', message, context),
    error: (message, context = {}) => log('ERROR', message, context),
    step: (message, context = {}) => log('INFO', message, { step: true, ...context }),
    start: (message, context = {}) => log('INFO', message, { phase: 'start', ...context }),
    complete: (message, context = {}) => log('INFO', message, { phase: 'complete', ...context }),
    tracePayload: (label, value, context = {}) => log('TRACE', `${label}: ${maskSensitivePayload(value)}`, context),
  };
};

const correlationId = process.env.CORRELATION_ID ?? randomUUID().slice(0, 8);

export const logger = {
  info: (message: string, context: LoggerContext = {}): void => { writeLog('INFO', message, correlationId, context); },
  warn: (message: string, context: LoggerContext = {}): void => { writeLog('WARN', message, correlationId, context); },
  error: (message: string, context: LoggerContext = {}): void => { writeLog('ERROR', message, correlationId, context); },
  step: (message: string, context: LoggerContext = {}): void => { writeLog('INFO', message, correlationId, { step: true, ...context }); },
  start: (message: string, context: LoggerContext = {}): void => { writeLog('INFO', message, correlationId, { phase: 'start', ...context }); },
  complete: (message: string, context: LoggerContext = {}): void => { writeLog('INFO', message, correlationId, { phase: 'complete', ...context }); },
  tracePayload: (label: string, value: string, context: LoggerContext = {}): void => { writeLog('TRACE', `${label}: ${maskSensitivePayload(value)}`, correlationId, context); },
};