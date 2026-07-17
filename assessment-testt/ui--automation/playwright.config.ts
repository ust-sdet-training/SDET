import { defineConfig, devices } from "@playwright/test";
import { ENV } from "./src/config/env";

export default defineConfig({
  testDir: "./tests",

  fullyParallel: true,

  forbidOnly: !!process.env.CI,

  retries: process.env.CI ? 2 : 0,

  workers: process.env.CI ? 1 : undefined,

  timeout: ENV.timeout,

  use: {
    baseURL: ENV.baseUrl,
    browserName: ENV.browser,
    headless: ENV.headless,

    trace: "on",

    screenshot: "on",

    video: "on",
  },

  reporter: [["html"], ["allure-playwright"]],

  projects: [
    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
      },
    },

    // {
    //     name: "firefox",
    //     use: {
    //         ...devices["Desktop Firefox"]
    //     }
    // },

    // {
    //     name: "webkit",
    //     use: {
    //         ...devices["Desktop Safari"]
    //     }
    // }
  ],
});
