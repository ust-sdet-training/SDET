import { expect, test } from "../fixtures/app.fixture";

test.describe("Flaky Test Demonstration", () => {

  test("bad example: assert cart total before the async request has settled", async ({
    page,
    log,
    evidence,
  }) => {

    // Open Debug Lab page
    log.info("Opening Debug Lab page");
    await page.goto("/debug-lab");

    // Trigger asynchronous cart total refresh
    log.info("Refreshing cart total");
    await page.getByRole("button", { name: "Refresh cart total" }).click();

    // Intentionally use a very short timeout to demonstrate flaky behaviour
    log.warn("Verifying cart total with a short timeout");

    await expect(page.getByTestId("debug-cart-total"))
      .toHaveText("Rs. 9,197", { timeout: 680 });

    // Store execution evidence
    evidence.cartResponse = {
      expectedTotal: "Rs. 9,197",
      timeout: 680,
    };

    log.info("Cart total verification completed");
  });


  test("bad example: brittle locator depends on element position", async ({
    page,
    log,
    evidence,
  }) => {

    // Open product catalog
    log.info("Opening product catalog");
    await page.goto("/catalog");

    // Using nth() makes this test flaky if product order changes
    log.warn("Selecting second product using position-based locator");

    await page.locator(".product-grid article").nth(1).click();

    // Verify product page
    await expect(
      page.getByRole("heading", { name: "Travel Backpack" })
    ).toBeVisible();

    // Store execution evidence
    evidence.product = {
      selectedPosition: 2,
      expectedProduct: "Travel Backpack",
    };

    log.info("Verified product details page");
  });

});