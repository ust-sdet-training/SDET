import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  timeout: 60_000,
  fullyParallel: false,
  workers: process.env.CI ? 1 : undefined,
  retries: process.env.CI ? 2 : 0,
  reporter:[['blob']],
  //reporter: process.env.CI ? [["list"],["blob"]]:[["html"]],
  use: {
    baseURL: "https://www.nykaa.com/",
    trace: "retain-on-failure",
    screenshot: "only-on-failure",
    video: 'retain-on-failure',
    actionTimeout:10_000,
    NavigationTransition:30_000
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
