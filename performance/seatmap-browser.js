import { browser } from 'k6/browser';
import { check } from 'k6';

export const options = {
  scenarios: {
    ui: {
      executor: 'shared-iterations',
      vus: 1,
      iterations: 5,
      options: {
        browser: {
          type: 'chromium',
        },
      },
    },
  },

  thresholds: {
    checks: [
      'rate==1.0'
    ],

    browser_web_vital_lcp: [
      'p(95)<3000'
    ],

    browser_web_vital_fcp: [
      'p(95)<2500'
    ]
  }
};

export default async function () {

  const page = await browser.newPage();

  try {

    await page.goto(
      'https://tripstack.doomple.com/buses/BUS-BOMDEL-04/seatmap',
      {
        waitUntil: 'networkidle'
      }
    );

    await page.locator('[data-seat]').first().waitFor();

    check(
      await page.locator('[data-seat]').count(),
      {
        'seat map rendered': (count) => count > 0
      }
    );

  } finally {

    await page.close();
  }
}