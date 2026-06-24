import { test, expect } from '@playwright/test';

test("Open home page, search for a product", async ({ page }) => {
    await page.goto("https://www.nykaa.com/");
    await expect(page).toHaveURL("https://www.nykaa.com/");
    await expect(page.getByPlaceholder("Search on Nykaa")).toBeEnabled();
    await page.getByPlaceholder("Search on Nykaa").fill("shampoo");
    await page.getByPlaceholder("Search on Nykaa").press("Enter");
    await expect(page.getByText("Buy Shampoos Online")).toBeVisible();
    await expect(page.getByText("Buy Shampoos Online").locator("span")).toHaveText(/([0-9])/);
});