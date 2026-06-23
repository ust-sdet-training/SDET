import { test, expect } from '@playwright/test';
import { E2EFlow } from '../flows/E2EFlow';

test('Testing the product is visible after searching it', async ({ page }) => {
    const flow = new E2EFlow(page);
    await page.goto('https://www.shoppersstop.com/');
    await flow.search_product('Shirts');
    await expect(page.getByText('Shirts')).toBeVisible();
});