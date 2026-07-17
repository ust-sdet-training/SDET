import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({

  testDir: './tests',

  fullyParallel: false,

  forbidOnly: !!process.env.CI,

  retries: process.env.CI ? 2 : 0,

  workers: process.env.CI ? 1 : 1,

  reporter: [
    ['list'],
    ['html', { outputFolder: 'playwright-report', open: 'never' }],
    ['json', { outputFile: 'reports/report.json' }],
    ['junit', { outputFile: 'reports/results.xml' }]
  ],

  use: {

    baseURL: process.env.BASE_URL,

    headless: process.env.CI
      ? true
      : process.env.HEADLESS === 'true',

    viewport: {
      width: 1536,
      height: 864
    },

    ignoreHTTPSErrors: true,

    actionTimeout: 10000,

    navigationTimeout: 30000,

    screenshot: 'on',

    video: 'on',

    trace: 'on'

  },

  projects: [

    {
      name: 'Google Chrome',

      use: {
        ...devices['Desktop Chrome'],
        channel: 'chrome'
      }

    }

  ],

  timeout: 60000,

  expect: {
    timeout: 10000
  },

  outputDir: 'test-results'

});