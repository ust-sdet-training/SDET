import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  timeout: 80_000,
  fullyParallel:true,
  workers:process.env.CI ? 4:undefined,
  retries: process.env.CI ? 2 : 0,
  reporter: [["html"], ["list"] ,["blob"]],
  
  use: {
    baseURL: process.env.BASE_URL || "https://www.shoppersstop.com/",
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video:"retain-on-failure",
    
      launchOptions: {
    slowMo: 1000, 
  },

  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
