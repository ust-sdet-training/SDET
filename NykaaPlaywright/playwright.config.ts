import { defineConfig, devices } from '@playwright/test';


export default defineConfig({
  timeout:60000,
  testDir: './tests',
 // retries: 1,
  workers: 1,
  reporter: 'html',
  use: {
  
     baseURL: 'https://www.nykaa.com/',
    trace: 'on-first-retry',
    launchOptions:{
    //  slowMo:500
    }
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
      
    },
  ],

});
