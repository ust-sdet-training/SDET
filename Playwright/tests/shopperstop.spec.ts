import { test, expect } from '@playwright/test';

test("Open the home page, search for shoes, verify search results", async ({ page }) => {
    await page.goto("https://www.shoppersstop.com/");
    await expect(page.getByRole("link", {name: "home"}));
    await page.getByPlaceholder("Search").click();
    await page.getByPlaceholder("Search").fill("shoes");
    await page.getByPlaceholder("Search").press("Enter");
    await expect(page.getByAltText("product card").first()).toBeVisible();
});