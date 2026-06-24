import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('https://www.nykaa.com/',{waitUntil:'domcontentloaded'});
  await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('face masks');
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
  await expect(page.getByRole('heading', { name: 'All Products' })).toBeVisible();
  const page1Promise = page.waitForEvent('popup');
  await page.getByRole('link', { name: 'Nykaa Skin Real Collagen Mask' }).click();
  const page1 = await page1Promise;
  await expect(page1.getByRole('heading', { name: 'Nykaa Skin Real Collagen Mask – Hydrates, Brightens And Gives Dewy Skin' })).toBeVisible();
});

test('Negative Test', async ({ page }) => {
  await page.goto('https://www.nykaa.com/',{waitUntil:"domcontentloaded"});
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('laharbkd jdkafjb');
  await page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
  await expect(page.getByText('Unfortunately, we couldn’t')).toBeVisible();
  await page.getByRole('button', { name: 'Back to Home' }).click();
});