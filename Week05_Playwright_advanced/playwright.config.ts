import { defineConfig, devices } from "@playwright/test";
import process from "node:process";

const CI = !!process.env.CI;

export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  fullyParallel: true,

  retries: CI ? 2 : 0,

  reporter: CI
    ? [["list"], ["blob"], ["html", { open: "never" }]]
    : [["html"]],

  use: {
    baseURL: process.env.BASE_URL || "http://localhost:5173",

    screenshot: CI ? "only-on-failure" : "off",

    trace: CI ? "on-first-retry" : "retain-on-failure",

    
    video: CI ? "retain-on-failure" : "on",
  },

  projects: [
    // {
    //   name: "chromium",
    //   use: {
    //     ...devices["Desktop Chrome"],
    //   },
    // },
    {
      name: "webkit",
      use: {
        ...devices["Desktop Safari"],
      },
    },
  ]
});
