import { expect } from '@playwright/test';
import { test } from '../fixtures/fixtures';

test('search for watches using ShopFlow', async ({ shopFlow ,page}) => {
  await shopFlow.searchForWatch(page);
   await expect(page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
  await expect(page.getByText('BESTSELLER').first()).toBeVisible();
});

test('shows no results for invalid search', async ({ shopFlow }) => {
  await shopFlow.searchInvalidProduct();
  await shopFlow.openBeautyBonanza();
});
