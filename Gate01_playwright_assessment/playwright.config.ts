import { defineConfig, devices } from "@playwright/test";
 
export default defineConfig({
  testDir: "./tests",
  timeout: 40_000,
  fullyParallel: false,
  workers: process.env.CI ? 1 : undefined,
  retries: process.env.CI ? 2 : 0,
  reporter: process.env.CI ? [["list"]] : [["blob"]],
  use: {
    baseURL: process.env.BASE_URL || "https://www.easemytrip.com/",
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video: "retain-on-failure",
    actionTimeout:10_000,
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});