import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  timeout: 60_000,
  fullyParallel: false,
  workers: process.env.CI ? 1 : undefined, 
  retries: process.env.CI ? 2 : 0,
  reporter: process.env.CI ? [["list"]] : [["html"]],
  use: {
    baseURL: process.env.BASE_URL || "http://www.nykaa.com",
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video: "retain-on-failure",
    headless: false
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
