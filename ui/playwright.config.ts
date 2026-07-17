import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({

  testDir: './tests',

  fullyParallel: false,

  workers: process.env.CI ? 1 : 1,

  retries: process.env.CI ? 2 : 0,

  timeout: 60000,

  expect: {
    timeout: 10000
  },

  outputDir: 'test-results',

  reporter: [
    ['list'],
    ['html', {
      outputFolder: 'playwright-report',
      open: 'never'
    }],
    ['json', {
      outputFile: 'reports/report.json'
    }],
    ['junit', {
      outputFile: 'reports/results.xml'
    }]
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

    actionTimeout: 10000,

    navigationTimeout: 30000,

    ignoreHTTPSErrors: true,

    screenshot: 'on',

    trace: 'on',

    video: 'on',

    locale: 'en-IN',

    timezoneId: 'Asia/Kolkata'
  },

  projects: [

    {

      name: 'Google Chrome',

      use: {
        ...devices['Desktop Chrome'],
        channel: 'chrome'
      }

    }

  ]

});