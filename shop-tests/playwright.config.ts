import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  fullyParallel: true,
  retries: process.env.CI ? 1 : 0,
  workers: process.env.CI ? 4 : undefined,
  reporter: process.env.CI ? [["blob"], ["list"]] : [["html"]],
  use: {
    baseURL: process.env.BASE_URL || "http://localhost:5173",
     trace: "on-first-retry",
     screenshot: "only-on-failure",
    video: "retain-on-failure",
    launchOptions: {
      slowMo: 1000,
    }
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
