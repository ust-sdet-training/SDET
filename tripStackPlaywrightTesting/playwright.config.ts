import { defineConfig } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
  workers: 1,
  retries: 1,

  use: {
    baseURL: process.env.BASE_URL,
    headless: !!process.env.CI,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'on-first-retry'
  }
});

/*

import { defineConfig } from '@playwright/test';

export default defineConfig({
  workers: 1,
  retries: 1,

  use: {
    baseURL: process.env.BASE_URL,
    headless: !!process.env.CI,
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'on-first-retry'
  }
});


*/
