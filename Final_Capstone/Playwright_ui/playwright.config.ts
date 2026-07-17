import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';
import path from 'path';

dotenv.config({
  path: path.resolve(__dirname, '.env'),
});

export default defineConfig({

  testDir: './tests',

  fullyParallel: false,

  forbidOnly: !!process.env.CI,

  retries: process.env.CI ? 1 : 0,

  workers: process.env.CI ? 1 : undefined,

  reporter: 'html',

  use: {

    baseURL: process.env.BASE_URL,

    headless: false,

    trace: 'on',

    video: 'retain-on-failure',

    screenshot: 'only-on-failure',

    actionTimeout: 30000,

    navigationTimeout: 60000,

  },

  projects: [
    {
      name: 'chromium',
      use: {
        ...devices['Desktop Chrome'],
      },
    },
  ],

});