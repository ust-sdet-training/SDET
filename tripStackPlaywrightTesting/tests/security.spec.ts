import { test, expect } from '@playwright/test';

test.describe('UI Security', () => {

    test('Verify user cannot access My Trips without login', async ({ page }) => {

        await page.goto('/my-trips');

        await expect(page).toHaveURL(/login/i);

    });

    test('Verify user is redirected after removing session', async ({ page }) => {

        await page.goto('/');

        await page.evaluate(() => {

            localStorage.clear();
            sessionStorage.clear();

        });

        await page.reload();

        await page.goto('/my-trips');

        await expect(page).toHaveURL(/login/i);

    });

    test('Verify protected page cannot be opened after logout', async ({ page }) => {

        await page.goto('/');

        await page.evaluate(() => {

            localStorage.clear();
            sessionStorage.clear();

        });

        await page.goto('/my-trips');

        await expect(page).toHaveURL(/login/i);

    });

});