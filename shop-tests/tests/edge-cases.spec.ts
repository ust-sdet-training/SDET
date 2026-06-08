import { expect, test } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { ProductsPage } from '../pages/ProductsPage';
import { CheckoutPage } from '../pages/CheckoutPage';
import { SearchPage } from '../pages/SearchPage';

test('Login with invalid credentials', async ({ page }) => {

    const loginPage = new LoginPage(page);

    await loginPage.goto();

    await loginPage.login(
        'invalid@test.com',
        'WrongPassword'
    );

    await expect(page.getByText('Invalid credentials')).toBeVisible();

    await expect(page).toHaveURL(/\/login$/);
});

test('Search with empty value', async ({ page }) => {

    const productsPage = new ProductsPage(page);

    await page.goto('/catalog');

    await productsPage.searchProduct('');

    await expect(productsPage.results()).toContainText('12');
});

test('Search non existing product', async ({ page }) => {

    const searchPage = new SearchPage(page);

    await page.goto('/catalog');

    await searchPage.searchNoResults('xyz123');
});

// test("Order validation", async ({ page }) => {

//     const checkoutPage = new CheckoutPage(page);

//     await page.goto("/checkout")

//     const orderPromise = page.waitForResponse(
//         res => res.url().includes('api/orders') && res.status() === 201
//     );

//     await checkoutPage.placeOrder();

//     const orderResponse  = await orderPromise;

//     const order = await orderResponse.json();

//     const orderId = order.id;

//     const productName = order.items[0].name;

//     await page.getByRole('link', {name: 'Orders'}).click();

//     await expect(page.getByText(orderId)).toBeVisible();

//     await expect(page.getByText(productName)).toBeVisible();

//     await expect(page.getByText('Placed')).toBeVisible();

//     const firstOrder = page.locator('[data-testid="order"]').first();

//     await expect(firstOrder).toContainText(orderId);
// })

