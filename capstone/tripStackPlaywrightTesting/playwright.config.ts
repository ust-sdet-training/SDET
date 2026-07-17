import { defineConfig } from '@playwright/test';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
  testDir: './tests',

  timeout: 60000,

  expect: {
    timeout: 10000
  },

  reporter: [
    ['html']
  ],

  use: {
    baseURL: process.env.BASE_URL,
    headless: process.env.HEADLESS === 'true',

    screenshot: 'only-on-failure',

    trace: 'retain-on-failure',

    video: 'retain-on-failure'
  }
});