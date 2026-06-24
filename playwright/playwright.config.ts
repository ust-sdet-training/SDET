import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  timeout: 50_000,
  fullyParallel: false,

  workers: 1,

  retries: 0,

  reporter: 'html',

  use: {
    browserName: 'chromium',
    headless: false,
    baseURL: 'https://www.nykaa.com/',
    trace: 'off',
    screenshot: 'only-on-failure',
    video: "retain-on-failure",
    actionTimeout: 10,
    navigationTimeout: 30
  },

  projects: [
    {
      name: 'chromium',
      use: {
        ...devices['Desktop Chrome']
      }
    }
  ]
});