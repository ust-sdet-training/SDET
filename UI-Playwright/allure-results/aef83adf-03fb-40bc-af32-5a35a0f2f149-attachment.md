# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> has title
- Location: tests\example.spec.ts:5:6

# Error details

```
Error: page.waitForTimeout: Target page, context or browser has been closed
```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | import { Logger } from '../utils/Logger';
  3  | import { info } from 'winston';
  4  | 
  5  | test.only('has title', async ({ page }) => {
  6  |   await page.goto('https://tripstack.doomple.com/flights/results?from=DEL&to=BLR&cls=ECONOMY&pax=1');
> 7  |   await page.waitForTimeout(5000); // Wait for 5 seconds to allow the page to load completely
     |              ^ Error: page.waitForTimeout: Target page, context or browser has been closed
  8  |   const slider = page.locator('#price-range');
  9  | 
  10 | await slider.evaluate((el: HTMLInputElement) => {
  11 |     el.value = '600000';
  12 |     el.dispatchEvent(new Event('input', { bubbles: true }));
  13 |     el.dispatchEvent(new Event('change', { bubbles: true }));
  14 | });
  15 | await page.waitForTimeout(5000); 
  16 | 
  17 | });
  18 | 
  19 | test('get started link', async ({ page }) => {
  20 |   Logger.info('Navigating to the home page and clicking on the Get Started link');
  21 |   await page.goto('/');
  22 |   await page.getByRole('link', { name: 'Get started' }).click();
  23 |   await expect(page.getByRole('heading', { name: 'Installation' })).toBeVisible();
  24 |   Logger.info('Get Started link clicked and Installation heading is visible');
  25 | });
  26 | 
  27 | 
```