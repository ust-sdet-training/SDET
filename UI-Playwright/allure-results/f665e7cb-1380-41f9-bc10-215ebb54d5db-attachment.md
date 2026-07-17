# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> has title
- Location: tests\example.spec.ts:5:6

# Error details

```
Test timeout of 30000ms exceeded.
```

```
Error: locator.evaluate: Test timeout of 30000ms exceeded.
Call log:
  - waiting for locator('#price-range')

```

# Page snapshot

```yaml
- generic [active] [ref=e1]:
  - heading "502 Bad Gateway" [level=1] [ref=e3]
  - separator [ref=e4]
  - generic [ref=e5]: nginx/1.31.3
```

# Test source

```ts
  1  | import { test, expect } from '@playwright/test';
  2  | import { Logger } from '../utils/Logger';
  3  | import { info } from 'winston';
  4  | 
  5  | test.only('has title', async ({ page }) => {
  6  |   await page.goto('https://tripstack.doomple.com/');
  7  |   await page.waitForTimeout(5000); // Wait for 5 seconds to allow the page to load completely
  8  |   const slider = page.locator('#price-range');
  9  | 
> 10 | await slider.evaluate((el: HTMLInputElement, value) => {
     |              ^ Error: locator.evaluate: Test timeout of 30000ms exceeded.
  11 |     el.value = String(value);
  12 |     el.dispatchEvent(new Event('input', { bubbles: true }));
  13 |     el.dispatchEvent(new Event('change', { bubbles: true }));
  14 | }, 600000);
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