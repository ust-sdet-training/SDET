import { test } from '@playwright/test';
import { ShopFlow } from '../flows/ShopFlow';

test('Checkout Flow', async ({ page }) => {
    await page.goto('/home');
    const flow = new ShopFlow(page);
    await flow.signIn();
    await flow.loginIn(
        'customer@example.com',
        'Password@123'
    );
    await flow.goToCatalog('Products');
    await flow.search('Running');
    await flow.addToCart();
    await flow.proceedToCheckout();
    await flow.placeOrder();
});