import { expect, test } from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";


test.describe("Happy Path", () => {
    test.beforeEach(async ({ page}) => {
        await page.goto("/", {waitUntil: 'domcontentloaded'});
    })
    test("search for Sanitizer", async ({ page }) => {
        await page.goto('/');
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
        await expect(page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
        const search = new SearchPage(page);
        await search.search('Sanitizer');
        await expect(page.getByRole('heading', { name: 'Perenne Makeup Disinfectant Mist' })).toBeVisible();
        const page1Promise = page.waitForEvent('popup');
        await page.getByRole('link', { name: 'Perenne Makeup Disinfectant' }).click();
        const productpage = await page1Promise;
        await expect(productpage.getByRole('heading', { name: 'Perenne Makeup Disinfectant Mist (Sanitizes Make Up Pallete And Tools) 200ml (' })).toBeVisible();
    })
    test("search for Shoes", async ({ page }) => {
        await page.goto('/');
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
        await expect(page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
        const search = new SearchPage(page);
        await search.search('Shoes');
        await expect(page.getByRole('heading', { name: 'Shoetopia Daily Wear Casual White Sneakers for Women' })).toBeVisible();
        const page1Promise = page.waitForEvent('popup');
        await page.getByRole('link', { name: 'Shoetopia Daily Wear Casual White Sneakers for Women' }).click();
        const productpage = await page1Promise;
        await expect(productpage.getByRole('heading', { name: 'Shoetopia Daily Wear Casual White Sneakers for Women (EURO 38)' })).toBeVisible();
    })


});

