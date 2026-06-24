import { expect, test } from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";


test.describe("Negative path", () => {
    test.beforeEach(async ({ page}) => {
        await page.goto("/", {waitUntil: 'domcontentloaded'});
    })
    test("Search for invalid or not found product", async ({ page }) => {
        await page.goto('/');
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
        await expect(page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
        const searchPage = new SearchPage(page);
        await searchPage.search('abcdefghijklmnopqrstuvwxyz');
        await expect(page.getByText('Thanks for visiting our')).toBeVisible();


    })
    test("Search using invalid text", async ({ page }) => {
        await page.goto('/');
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
        await expect(page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
        const searchPage = new SearchPage(page);
        await searchPage.search('av123131saq');
        await expect(page.getByText('Thanks for visiting our')).toBeVisible();


    })


});

