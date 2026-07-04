import { expect, test } from "@playwright/test";
 
const flakyDemo =
  process.env.W5D1_SHOW_FLAKES === "true"
    ? test.describe
    : test.describe.skip;
 
flakyDemo("Intentionally flaky example", () => {
 
  test("bad example: asserts cart total before the async request has settled", async ({ page }) => {
    // Open the debug page that contains the cart total UI.
    await page.goto("/debug-lab");
 
    // Trigger the refresh action that updates the cart total.
    await page.getByRole("button", { name: "Refresh cart total" }).click();
 
    // Check the cart total immediately, which is the flaky part.
    await expect(page.getByTestId("debug-cart-total")).toContainText("Rs. 9,197");
  });
 
  test("bad example: brittle product locator depends on card position", async ({ page }) => {
    // Open the product catalog page.
    await page.goto("/catalog");
 
    // Navigate to a product using a fragile position-based selector.
    await page.getByRole("link", {name: "View Travel Backpack"}).click();
 
    // Assert that the expected product page is visible.
    await expect(
      page.getByRole("heading", { name: "Travel Backpack" })
    ).toBeVisible();
  });
 
});
 