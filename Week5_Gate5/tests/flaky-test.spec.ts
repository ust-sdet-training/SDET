import { test, expect } from '@playwright/test';

test("valid customer can sign in", async ({ page }) => {
    page.goto("/login")

    page.getByLabel("Email").fill("customer@example.com");
    page.getByLabel("Password").fill("Password@123");
    page.locator('css=button.primary').click();
});