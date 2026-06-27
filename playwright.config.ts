import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',

  fullyParallel: true,

  workers: 1,

  timeout: 120000,

  reporter: 'html',

  use: {
    baseURL: 'https://www.nykaa.com/',

    headless: false,

    actionTimeout: 10000,

    navigationTimeout: 60000,

    trace: 'on-first-retry',

    screenshot: 'only-on-failure',

    video: 'retain-on-failure',
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});