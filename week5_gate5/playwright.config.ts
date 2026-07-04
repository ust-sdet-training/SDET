import { defineConfig, devices } from "@playwright/test";

const CI = !!process.env.CI;

export default defineConfig({
  testDir: "./tests",

  timeout: 30_000,

  fullyParallel: true,

  retries: CI ? 2 : 0,

  reporter: CI
    ? [
        ["list"],
        ["blob"],
        ["html", { open: "never" }]
      ]
    : [["html", { open: "never" }]],

  use: {
  baseURL: "http://localhost:5173",
  screenshot: "retain-on-failure",
  trace: "retain-on-failure",
  video: "retain-on-failure"
},

  projects: [
    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
      },
    },
  ],
});