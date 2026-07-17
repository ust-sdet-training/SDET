import { randomUUID } from 'node:crypto';
import { maskSensitivePayload, maskValue } from './maskUtil';

type LoggerContext = Record<string, string | number | boolean | undefined>;

const secretValues = (): string[] =>
  Object.entries(process.env)
    .filter(([key, value]) => Boolean(value) && /(?:PASSWORD|PAYMENT|PASSENGER|TRIPSTACK_EMAIL)/.test(key))
    .map(([, value]) => value as string);

const correlationId = process.env.CORRELATION_ID ?? randomUUID().slice(0, 8);

const buildContext = (context: LoggerContext = {}): LoggerContext => ({
  correlationId,
  timestamp: new Date().toISOString(),
  environment: process.env.NODE_ENV ?? 'local',
  ...context,
});

export const redact = (message: string): string =>
  secretValues().reduce((safeMessage, secret) => safeMessage.replaceAll(secret, maskValue(secret)), message);

const emit = (level: 'INFO' | 'WARN' | 'ERROR' | 'TRACE', message: string, context: LoggerContext = {}): void => {
  const entry = {
    level,
    message: redact(message),
    ...buildContext(context),
  };

  const output = JSON.stringify(entry);

  if (level === 'WARN') {
    console.warn(output);
  } else if (level === 'ERROR') {
    console.error(output);
  } else {
    console.log(output);
  }
};

export const logger = {
  info: (message: string, context: LoggerContext = {}): void => emit('INFO', message, context),
  warn: (message: string, context: LoggerContext = {}): void => emit('WARN', message, context),
  error: (message: string, context: LoggerContext = {}): void => emit('ERROR', message, context),
  step: (message: string, context: LoggerContext = {}): void => emit('INFO', message, { step: true, ...context }),
  start: (message: string, context: LoggerContext = {}): void => emit('INFO', message, { phase: 'start', ...context }),
  complete: (message: string, context: LoggerContext = {}): void => emit('INFO', message, { phase: 'complete', ...context }),
  tracePayload: (label: string, value: string, context: LoggerContext = {}): void => {
    emit('TRACE', `${label}: ${maskSensitivePayload(value)}`, context);
  },
};
