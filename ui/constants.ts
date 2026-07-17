export const APP_BASE_URL = process.env.PLAYWRIGHT_BASE_URL ?? 'https://tripstack.doomple.com';

export const APP_PATHS = {
  login: '/login',
  busSearch: '/buses/search',
} as const;

export const DEFAULT_TIMEOUTS = {
  short: 10_000,
  medium: 15_000,
  long: 30_000,
} as const;
