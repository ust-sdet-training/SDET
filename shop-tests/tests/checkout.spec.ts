import { test, expect } from '@playwright/test';

test('checkout', async ({ page }) => {
    await page.goto('/');
    await page.getByRole('link', { name: 'Checkout', exact: true }).click();
    await page.getByRole('link', { name: 'Products' }).click();
    await page.getByRole('link', { name: 'View Running Shoes' }).click();
    await page.getByRole('button', { name: 'Add to cart' }).click();
    await page.getByRole('button', { name: 'Proceed to checkout' }).click();
    await expect.soft(page.getByLabel('Checkout totals').getByText('Rs. 4,499')).toBeVisible();
    await page.getByRole('button', { name: 'Place order' }).click();
    await expect.soft(page.getByRole('heading', { name: 'Thank you for your order' })).toBeVisible();
});