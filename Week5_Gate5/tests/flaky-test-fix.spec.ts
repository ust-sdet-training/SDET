import { test, expect } from '@playwright/test';

test("valid customer can sign in", async ({ page }) => {
    await page.goto("/login")

    await page.getByLabel("Email").fill("customer@example.com");
    await page.getByLabel("Password").fill("Password@123");
    await page.locator('css=button.primary').click();
});