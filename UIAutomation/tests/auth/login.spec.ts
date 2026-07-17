import { test, expect } from '@playwright/test';
import { LoginPage } from '../../pages/auth/LoginPage';

test(
    'Verify user login to TripStack',
    async ({ page }) => {

        const loginPage =
            new LoginPage(page);

        await loginPage.open();

        await loginPage.login(
            'niaj@tripstack.test',
            'Password@123'
        );

        await expect(
            page.getByRole(
                'tab',
                {
                    name: 'Buses'
                }
            )
        ).toBeVisible();
    }
);