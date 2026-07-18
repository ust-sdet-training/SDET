import { browser } from 'k6/browser';
import { expect } from 'https://jslib.k6.io/k6-testing/0.3.0/index.js';
import { Options } from 'k6/options';


export const options: Options = {
  scenarios: {
    ui_performance: {
      executor: 'constant-vus',
      vus: 2,
      duration: '30s',
      options: {
        browser: {
          type: 'chromium',
        },
      },
    },
  },
  thresholds: {
    browser_web_vital_lcp: ['p(95) < 3000'],
  },
};

export default async function (): Promise<void> {
  const page = await browser.newPage();

  try {
    await page.goto('https://tripstack.doomple.com/login');
 
    const baseUrl = __ENV.ARAVIND_BASE_URL_UI;
    const password = __ENV.ARAVIND_PASSWORD;;

    await page.locator('input[type="email"]').fill("carol@tripstack.test");
    await page.locator('input[type="password"]').fill(password);
    
    await Promise.all([
      page.waitForNavigation(),
      page.locator('button[type="submit"]').click(),
    ]);

    await page.goto('https://tripstack.doomple.com/my-trips');
    await page.waitForLoadState('networkidle');

    await expect(page).toHaveTitle(/./);

  } finally {
    await page.close();
  }
}