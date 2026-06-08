import { test, expect} from '../fixtures/test';
import { testUsers } from '../fixtures/test-users';

test.describe('login test', () => {
    test('success login path', async ({ shop, page }) => {
        // await page.goto('/login');
        // await expect(page.getByRole('heading', { name: 'Sign in to Retail Lab'}));

        // const loginForm = page.getByRole('form', { name: 'Login' });
        // await loginForm.getByLabel('Email').fill(testUsers.customer.email);
        // await loginForm.getByLabel("Password").fill(testUsers.customer.password);
        // await loginForm.getByRole("button", { name: 'Sign in'}).click();
        await shop.loginSuccessFlow();
    });

    test('invalid login test', async ({ shop, page }) => {
        await shop.loginFailureFlow();
    });
});