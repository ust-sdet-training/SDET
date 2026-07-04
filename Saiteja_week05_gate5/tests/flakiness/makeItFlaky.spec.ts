import { expect, test } from "../../fixtures/app.fixture";
 
const flakyDemo =
  process.env.W5D1_SHOW_FLAKES === "true"
    ? test.describe
    : test.describe.skip;
 
flakyDemo("Intentionally flaky example", () => {
 
  test("bad example: asserts cart total before the async request has settled", async ({ page, log, evidence }) => {
    // Open the debug page that contains the cart total UI.
    log.info("Opening debug lab page for flaky cart total check");
    await page.goto("/debug-lab");
 
    // Trigger the refresh action that updates the cart total.
    log.info("Refreshing cart total");
    await page.getByRole("button", { name: "Refresh cart total" }).click();
 
    // Check the cart total immediately, which is the flaky part.
    const cartTotalText = await page.getByTestId("debug-cart-total").textContent();
    log.info("Captured cart total text", { cartTotalText });
    evidence.cartTotalText = cartTotalText;

    // Flaky approach: uses a short timeout that may fail.
    // await expect(page.getByTestId("debug-cart-total")).toContainText("Rs. 9,197" , {
    //   timeout: 620
    // });

    // Stable approach: relies on Playwright's built-in auto-waiting.
    await expect(page.getByTestId("debug-cart-total")).toContainText("Rs. 9,197");
  });
 
  test("bad example: brittle product locator depends on card position", async ({ page, log, evidence }) => {
    // Open the product catalog page.
    log.info("Opening catalog page for brittle locator check");
    await page.goto("/catalog");
 
    // Navigate to a product using a fragile position-based selector.
    log.info("Clicking the travel backpack product link");
    // Flaky approach: uses a short timeout that may fail.
    // await page.locator(".product-grid article:nth-child(2) a").click();

    // Stable approach: relies on Playwright's built-in auto-waiting.
    await page.getByRole("link", {name: "View Travel Backpack"}).click();
 
    // Assert that the expected product page is visible.
    const headingText = await page.getByRole("heading", { name: "Travel Backpack" }).textContent();
    log.info("Captured product page heading", { headingText });
    evidence.productHeading = headingText;

    await expect(
      page.getByRole("heading", { name: "Travel Backpack" })
    ).toBeVisible();
  });
 
});
 
