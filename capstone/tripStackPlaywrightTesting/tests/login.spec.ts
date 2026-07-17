import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { LoginFlow } from '../flows/LoginFlow';
import { employee } from '../utils/testData';



test.describe('Login Feature', () => {

    test('Verify user can login with valid credentials', async ({ page }) => {

        const loginPage = new LoginPage(page);
        const loginFlow = new LoginFlow(loginPage);

        await loginFlow.login(
        employee.email,
        employee.password
    );

        await expect(page).toHaveURL(/.*tripstack.*/);

        await expect(
            page.getByRole('heading', {
                name: 'Book flights & buses across'
            })
        ).toBeVisible();

    });

    test('Verify login with invalid password', async ({ page }) => {

    const loginPage = new LoginPage(page);

    await loginPage.navigate();

    await loginPage.openLogin();

    await loginPage.enterEmail(process.env.EMAIL!);

    await loginPage.enterPassword('WrongPassword');

    await loginPage.clickSignIn();

});

});

