import { defineConfig, devices } from "@playwright/test";

export default defineConfig({

  testDir: "./tests",

  timeout: 30000,

  fullyParallel: true,

  reporter: [
    ["html"],
    ["list"]
  ],

  use: {

    baseURL:
      "http://localhost:5173",

    screenshot: "on",

    trace: "on",

    video: "on",

    headless: true
  },

  projects: [
    {
      name: "chromium",

      use: {
        ...devices["Desktop Chrome"]
      }
    }
  ]
});

