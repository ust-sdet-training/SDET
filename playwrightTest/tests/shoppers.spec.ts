import { test, expect } from '@playwright/test';

test('Search Product', async ({ page }) => {
  await page.goto('https://www.shoppersstop.com/');

  await expect(page).toHaveTitle(/Shoppers Stop/i);

  const searchBox = page.locator('input[placeholder*="Search"]');
  await searchBox.fill('shirt');
  await searchBox.press('Enter');

  await expect(page.locator('body')).toContainText(/shirt/i);
});