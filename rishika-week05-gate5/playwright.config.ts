import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  retries: process.env.CI ? 1 : 0,
  reporter: [["html"], ["list"]],
  use: {
  baseURL: "http://localhost:5173",
  screenshot: "only-on-failure",
  trace: "retain-on-failure",
  video: "retain-on-failure"
},
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
