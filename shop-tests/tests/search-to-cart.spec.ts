import { expect, test } from '@playwright/test';

import { LoginPage } from '../pages/LoginPage';
import { ProductsPage } from '../pages/ProductsPage';
import { ProductDetailsPage } from '../pages/ProductDetailsPage';
import { CartPage } from '../pages/CartPage';
import { CheckoutPage } from '../pages/CheckoutPage';

import { testUsers } from '../fixtures/test-users';
import { SearchPage } from '../pages/SearchPage';

test.describe('Customer Purchase Journey', () => {

    test('Place an order', async ({ page }) => {

        const loginPage = new LoginPage(page);

        const productsPage = new ProductsPage(page);

        const searchPage = new SearchPage(page);

        const productPage = new ProductDetailsPage(page);

        const cartPage = new CartPage(page);

        const checkoutPage = new CheckoutPage(page);

        await loginPage.goto();

        await loginPage.login(
            testUsers.customer.email,
            testUsers.customer.password
        );

        await expect(page).toHaveURL(/\/home$/);

        await page.getByRole('link', {name: 'Preview products'}).click();

        await expect(page).toHaveURL(/\/catalog$/);

        await searchPage.search('Running Shoes');

        await productsPage.openProduct('Running Shoes');

        await expect(page.getByRole('heading', {name: 'Running Shoes'})).toBeVisible();

        const cartUpdatePromise = cartPage.waitForCartUpdate();

        await productPage.checkQuantity();

        await productPage.addToCart();

        await cartUpdatePromise;

        await expect(page.getByRole('heading', {name: 'Cart'})).toBeVisible();

        await expect(page.getByTestId('cart-count')).toHaveText('1');

        await cartPage.checkout();

        await expect(page).toHaveURL(/\/checkout$/);

        const orderPromise = page.waitForResponse(
                res => res.url().includes('/orders') && res.status() === 201
            );

        await checkoutPage.placeOrder();

        await orderPromise;

        // const productName = order.items[0].name;

        await expect(page.getByText('Thank you for your order')).toBeVisible();

        await page.getByRole('link', {name: 'Orders'}).click();

        // await expect(page.getByText(productName)).toBeVisible();

        await expect(page.getByTestId('orders-count')).toHaveCount(1);

    });
});