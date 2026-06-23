import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://www.shoppersstop.com/');
  await page.getByRole('link', { name: 'logo', exact: true }).click();
  await page.getByRole('textbox', { name: 'Search' }).click();
  await page.getByRole('textbox', { name: 'Search' }).fill('shoes');
  await page.getByRole('textbox', { name: 'Search' }).press('Enter');
});