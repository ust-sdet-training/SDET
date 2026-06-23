import { test, expect } from "@playwright/test";

test.describe("ShoppersStop UI test", () => {
    test("M1: test to check whether banner is visible", async ({page}) => {
        await page.goto("/home");
        
        await expect(page).toHaveURL(/\/home/);
        await expect(page.getByRole('link', { name: 'logo', exact: true })).toBeVisible();
    });

    test("M2: test to check search functionality", async ({ page }) => {
        await page.goto("/home");

        await expect(page.getByRole('textbox', { name: 'Search' })).toBeEmpty();
        await page.getByRole('textbox', { name: 'Search' }).click();
        await page.getByRole('textbox', { name: 'Search' }).fill('watches');
        await page.getByText('Watches', { exact: true }).nth(1).click();
        await expect(page.getByRole('heading', { name: 'Watch' })).toBeVisible();
    });

    test('M3: negative test to find no products', async ({ page }) => {
        await page.goto('/home');
        await page.getByRole('textbox', { name: 'Search' }).click();
        await page.getByRole('textbox', { name: 'Search' }).fill('notaproduct');
        await expect(page.getByRole('img', { name: 'magnifying_glass.svg' })).toBeVisible();
        await expect(page.getByText('No Results Found')).toBeVisible();
    });
});