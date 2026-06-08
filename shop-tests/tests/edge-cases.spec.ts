import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { SearchPage } from '../pages/SearchPage';
import { CartPage } from '../pages/CartPage';
import { ShopFlow } from '../flows/ShopFlow';


test.describe("Edge Cases", () => {
    test('invalid login', async ({ page }) => {

        const login = new LoginPage(page);

        await page.goto('/');

        await login.login(
            'wrong@test.com',
            'wrongpassword'
        );

        await expect(
            page.getByText('Invalid credentials')
        ).toBeVisible();
    });
    test('search unavailable product', async ({ page }) => {

        const search = new SearchPage(page);

        await search.search('Laptop123456');

        await expect(
            page.getByText('No products found')
        ).toBeVisible();
    });
    test('checkout empty cart', async ({ page }) => {

        const cart = new CartPage(page);

        await page.goto('/cart');

        await cart.checkout();

        await expect(
            page.getByText('Cart is empty')
        ).toBeVisible();
    });
    test('add out of stock product', async ({ page }) => {

        const shop = new ShopFlow(page);

        await shop.productSearch('Gaming Laptop');

        await expect(
            page.getByText('Out of Stock')
        ).toBeVisible();
    });
})