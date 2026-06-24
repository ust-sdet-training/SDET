import { defineConfig, devices } from '@playwright/test';

export default defineConfig({

  testDir: './tests',

  fullyParallel: false,   // ✅ avoid flakiness

  forbidOnly: !!process.env.CI,

  retries: process.env.CI ? 2 : 1,

  workers: 1,             // ✅ safer for UI sites

  timeout: 60000,         // ✅ increased

  expect: {
    timeout: 15000       // ✅ increased
  },

  reporter: [
    ['html'],
    ['list']
  ],

  use: {
    baseURL: 'https://www.nykaa.com/',

    headless: false,              // ✅ debugging
    actionTimeout: 15000,         // ✅ new
    navigationTimeout: 60000,     // ✅ new

    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure'
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