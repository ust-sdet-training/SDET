import { defineConfig, devices } from '@playwright/test';


export default defineConfig({
  // testDir: "./tests",
  testDir: './tests',
  timeout: 60_000,
  fullyParallel: false,
  workers: process.env.CI ? 1 : undefined,
  retries: process.env.CI ? 3 : 0,
  reporter: process.env.CI ? [["list"], ["blob"]] : [["html"]],  
  use: {
    baseURL: process.env.BASE_URL || "http://localhost:5173",
    actionTimeout: 10_000,
    navigationTimeout: 50_000,
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video: "retain-on-failure"
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    }
    // ,

    // {
    //   name: 'firefox',
    //   use: { ...devices['Desktop Firefox'] },
    // },

    // {
    //   name: 'webkit',
    //   use: { ...devices['Desktop Safari'] },
    // }
  ]
});

