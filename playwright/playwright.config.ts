import { defineConfig, devices } from "@playwright/test";
import env from "dotenv"

env.config();
export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  fullyParallel:true,
  workers:process.env.CI ? 4:undefined,
  retries: process.env.CI ? 2 : 0,
  reporter: [["html"], ["list"] ,["blob"]],
  
  use: {
    baseURL: process.env.BASE_URL,
    trace: "on-first-retry",
    screenshot: "only-on-failure",
    video:"retain-on-failure",
    
 

  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ]
});
