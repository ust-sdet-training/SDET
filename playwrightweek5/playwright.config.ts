import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  
  fullyParallel: false,
  forbidOnly: !!process.env.CI,


  /* Retry only on CI  in Local no Retry*/
  retries: process.env.CI ? 2 : 0,
  /*CI one worker and in local default worker*/
  workers: process.env.CI ? 1 : 1,

  /*After every test run in CI we will get blob,html(which will not open),and reports.xml in local html and reports.xml only*/
  reporter: process.env.CI ? [['blob'], ['html', { open: 'never' }],
            ['junit', { outputFile: 'reports.xml' }]]
            : [['html'],['junit', { outputFile: 'reports.xml' }]],
    
  use: {
    /* Base url from the terminal or from the ui*/
    baseURL: process.env.BASE_URL || 'http://localhost:5173',

    
    trace: !!process.env.CI ? 'retain-on-failure' : 'retain-on-failure',
    screenshot: !!process.env.CI ? 'only-on-failure' : 'only-on-failure',
    video: !!process.env.CI ? 'retain-on-failure': 'retain-on-failure',

  },

 projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    
    
  ],
});
