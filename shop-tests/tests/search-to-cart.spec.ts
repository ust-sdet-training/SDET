import { test, expect } from "../fixtures/test";
import { testProducts } from "../fixtures/test-products";

test.describe('search to order placed flow', () => {
    test('happy path', async ({ shop, page }) => {

        // //search
        // await page.goto('/catalog');
        // await expect(page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
        await shop.catalogPage.goto();
        
        // await page.getByLabel('Search products').fill(testProducts.product1.name);
        await shop.searchQuery(testProducts.product1.name);
        await expect(page.getByTestId('product-card').getByRole('heading', { name: testProducts.product1.name }).first()).toBeVisible();
        await page.getByRole('link', { name: testProducts.product1.name }).click();
         
        //product
        await expect(page).toHaveURL(/\/product/);
        await expect.soft(page.getByRole('heading', { name: testProducts.product1.name })).toBeVisible();
        await page.getByRole('button', { name: 'Add to cart' }).click();

        //cart
        await expect(page).toHaveURL(/\/cart/);

        await expect.soft(page.getByTestId('cart-count')).toHaveText('1');
        const cartPromise = page.waitForResponse(res => res.url().includes('/cart') && res.status() === 200);
        await page.getByRole('button', { name: 'Proceed to checkout' }).click();
        await cartPromise;
        //checkout
        await expect(page).toHaveURL(/\/checkout/);
        await expect.soft(page.getByRole('heading', { name: 'Checkout' }).first()).toBeVisible();
        await page.getByRole('button', { name: 'Place order' }).click();
        //const order_id = page.getByRole()
        //order
        await expect(page).toHaveURL(/\/checkout/);
        await expect.soft(page.getByRole('heading', { name: 'Thank you for your order'})).toBeVisible();

    });

    test('check if quantity is decreased', async ({ shop, page }) => {
        await shop.catalogPage.goto();
        await shop.searchQuery(testProducts.product1.name);
        await expect(page.getByTestId('product-card').getByRole('heading', { name: testProducts.product1.name }).first()).toBeVisible();
        await page.getByRole('link', { name: testProducts.product1.name }).click();
        
        //product
        await expect(page).toHaveURL(/\/product/);
        await expect.soft(page.getByRole('heading', { name: testProducts.product1.name })).toBeVisible();
        await page.getByRole('button', { name: 'Add to cart' }).click();

        //cart
        await expect(page).toHaveURL(/\/cart/);

        await expect.soft(page.getByTestId('cart-count')).toHaveText('1');
        const cartPromise = page.waitForResponse(res => res.url().includes('/cart') && res.status() === 200);
        await page.getByRole('button', { name: 'Proceed to checkout' }).click();
        await cartPromise;
        //checkout
        await expect(page).toHaveURL(/\/checkout/);
        await expect.soft(page.getByRole('heading', { name: 'Checkout' }).first()).toBeVisible();
        await page.getByRole('button', { name: 'Place order' }).click();
        //const order_id = page.getByRole()
        //order
        await expect(page).toHaveURL(/\/checkout/);
        await expect.soft(page.getByRole('heading', { name: 'Thank you for your order'})).toBeVisible();
        //await page.goto('/product/running-shoes');
    });
});