import {test, expect} from "@playwright/test";

test.describe('cart page test', () => {
    test('dialog', async ({ page }) => {
        await page.goto('/cart');
        await page.getByRole('button', { name: 'Continue shopping' }).click();
        await page.getByRole('link', { name: 'View Running Shoes' }).click();
        await page.getByRole('button', { name: 'Add to cart' }).click();
        page.once('dialog', dialog => {
            dialog.accept();
        });
        await expect(page.getByTestId('cart-count')).toHaveText('1');
        await page.getByRole('button', { name: 'Remove Running Shoes' }).click();
        await expect(page.getByTestId('cart-count')).toHaveText('0');
    });
});
