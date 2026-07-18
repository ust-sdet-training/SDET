import { test, expect } from '@playwright/test';
import { LoginPage } from '../../pages/auth/LoginPage';
import { ENV } from '../../utils/env';

test(
    'Verify user login to TripStack @smoke @auth',
    async ({ page }) => {

        const loginPage = new LoginPage(page);

        await loginPage.open();
        await loginPage.login(ENV.EMAIL, ENV.PASSWORD);

        await expect(
            page.getByRole('tab', { name: 'Buses' })
        ).toBeVisible();
    }
);