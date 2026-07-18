import { defineConfig, devices } from '@playwright/test';
import { config } from './utils/config';

const evidenceDir = 'artifacts';

export default defineConfig({
  testDir: './tests',
  timeout: 90_000,
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: (() => {
    const base = [
      ['list'],
      ['html', { outputFolder: `${evidenceDir}/html-report`, open: 'never' }],
    ] as any[];

   
  })(),
  snapshotPathTemplate: '{testDir}/{testFilePath}/{arg}{ext}',
  use: {
    baseURL: config.baseURL,
    headless: config.headless,
    launchOptions: { slowMo: config.slowMo },
    screenshot: 'on',
    video: 'on',
    trace: 'on',
    ignoreHTTPSErrors: true,
    testIdAttribute: 'data-testid',
  },
  outputDir: `${evidenceDir}/test-results`,
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
