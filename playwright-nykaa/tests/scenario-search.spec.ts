import { test, expect } from "../fixtures/test";

test.describe("Scenario - User should be able to navigate to website and search for a product", () => {
    test("NYK01: navigate to home page", async ({ page }) => {
        await page.goto('/', { waitUntil: "domcontentloaded"});

        await expect(page).toHaveURL(/nykaa/);
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
    });

    test("NYK02: search for a product that maps to categories", async ({ page, searchFix }) => {
        await searchFix.search('sun care');
        await expect(page.getByRole('heading', { name: /Buy/ })).toBeVisible();
    });

    test("NYK03: search for a product", async ({ page, searchFix }) => {
        await searchFix.search('sun screen');
        await expect(page.getByText(/results for/)).toBeVisible();
    });

    test("NYK04: search for a product that does not exist", async ({ page, searchFix }) => {
        await searchFix.search('abcd');

        await expect(page.getByText(/No results found/)).toBeVisible();
    });
});