import { test, expect } from '@playwright/test';

test("bad example: asserts cart total before the async request has settled", async ({ page }) => {
    // Open the Debug Lab page
    await page.goto("/debug-lab");
    // Click the button to refresh the cart total
    await page.getByRole("button", { name: "Refresh cart total" }).click();
    // Check if the cart total is displayed correctly
    await expect(page.getByTestId("debug-cart-total")).toHaveText("Rs. 9,197");
});