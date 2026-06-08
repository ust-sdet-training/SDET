import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./shop-tests",
  timeout: 30_000,
  retries: process.env.CI ? 1 : 0,
  reporter: [["html"], ["list"]],
  use: {
    baseURL: process.env.BASE_URL || "http://localhost:5173",
    trace: "on-first-retry",
    screenshot: "only-on-failure"
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
