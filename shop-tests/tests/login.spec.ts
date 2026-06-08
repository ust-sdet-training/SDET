import { test, expect } from '@playwright/test';
import { ShopFlow } from '../flows/ShopFlow';
test('User Login', async ({ page }) => {
    await page.goto('/home');
    const flow = new ShopFlow(page);
    await expect(flow.validateHomePage());
    await flow.signIn();
    await flow.loginIn(
        'customer@example.com',
        'Password@123'
    );
});