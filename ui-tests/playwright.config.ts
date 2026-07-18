import { defineConfig, devices } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config({ path: '.env' });

export default defineConfig({
  testDir: './tests',
  fullyParallel: true,
  timeout: 30_000,
  expect: { timeout: 10_000 },
  reporter: [['list'], ['allure-playwright']],
  use: {
    baseURL: process.env.BASE_URL || 'https://tripstack.doomple.com',
    headless: process.env.HEADLESS === 'true',
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure'
  },
  projects: [
    {
        name: 'chromium',
        use: { ...devices['Desktop Chrome'] }
        }
  ]
});
