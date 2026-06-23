import { test, expect } from '@playwright/test';

test('Checking search', async ({ page }) => {
  await page.goto('https://www.shoppersstop.com/');

  
  await expect(page).toHaveURL(/shoppersstop/i);

  await expect(page).toHaveTitle(/end of season sale/i);

  await page.getByPlaceholder("search").click();

  

  await page.getByPlaceholder("search").fill("test");

  await page.getByPlaceholder("search").press('Enter');
});
