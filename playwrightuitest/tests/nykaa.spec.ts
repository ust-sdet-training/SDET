import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://www.nykaa.com');
  await expect(page).toHaveTitle("Buy Cosmetics Products & Beauty Products Online in India at Best Price | Nykaa");

  
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('lipstick');
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
 
  await expect(page.locator('h1')).toContainText("Best Lip Makeup Online");

  await page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('dfgfdghjfdhjhk');
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
 
await page.goto('https://www.nykaa.com/search/result/?q=dfgfdghjfdhjhk');

await expect(
  page.getByText(/Unfortunately, we couldn’t find any matches/i)
).toBeVisible();
});