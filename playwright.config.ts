import { defineConfig, devices } from "@playwright/test";
 
export default defineConfig({
  // testDir: "./tests",
  timeout: 60_000,
  fullyParallel: false,
  workers: 1,
  retries: process.env.CI ? 2 : 0,
  reporter: process.env.CI
  ? [["list"], ["html"]]
  : [["html"], ["blob"]],
    use: {
    baseURL: process.env.BASE_URL || "https://www.nykaa.com/",
    actionTimeout:10_000,
    navigationTimeout:30_000,
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video: "retain-on-failure"
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});