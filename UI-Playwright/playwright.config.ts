import { defineConfig, devices } from '@playwright/test';
import { Env } from './utils/Env';

export default defineConfig({
  testDir: './tests',
  fullyParallel: true,
  timeout: 20 * 1000,
  retries: process.env.CI ? 1 : 0,
  workers: process.env.CI ? 1 : 4,
  reporter: [['html'], ['allure-playwright']],
  use: {
    baseURL: Env.get('BASE_URL'),
    trace: 'retain-on-failure',
    video: 'retain-on-failure',
    screenshot: 'only-on-failure',
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
