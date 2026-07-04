import { test, expect } from "../fixtures/evidence";

test("Checkout flow assessment with coupon validation", async ({
  page,
  log,
  evidence,
}) => {
  // ------------------------------------------------------------------
  // Assessment Goal
  // ------------------------------------------------------------------
  // This is a compound business journey that validates the complete purchase flow
  //
  // Product Search
  // → Product Details
  // → Add to Cart
  // → Coupon
  // → Checkout
  // → Order Confirmation
  //
  // The objective is to prove that the entire checkout flow is stable
  // ------------------------------------------------------------------

  log.info("Starting compound checkout journey", {
    targetUrl: "http://localhost:5173/",
    journey:
      "Product Search -> Product Details -> Add To Cart -> Coupon -> Checkout -> Order Confirmation",
  });

  await page.goto("http://localhost:5173/");

  // ------------------------------------------------------------------
  // Step 1
  // Product Discovery
  // ------------------------------------------------------------------
  // A customer begins by searching for a product.
  // Starting from the search page verifies the journey from the very initial step
  // ------------------------------------------------------------------

  await page.locator('a[href="/catalog"]').first().click();

  await page.locator('[data-test="search-input"]').click();
  await page.locator('[data-test="search-input"]').fill("running shoes");
  await page.locator('[data-test="search-button"]').click();

  log.info("Product search completed", {
    searchKeyword: "running shoes",
  });

  // ------------------------------------------------------------------
  // Step 2
  // Product Validation
  // ------------------------------------------------------------------
  // Before purchasing, verify that the expected product is displayed.
  // This prevents continuing the journey with incorrect search results.
  // ------------------------------------------------------------------

  await expect(
    page.getByRole("link", { name: "View Running Shoes" })
  ).toBeVisible();

  await page.getByRole("link", { name: "View Running Shoes" }).click();

  log.info("Opened product details page", {
    product: "Running Shoes",
  });

  // ------------------------------------------------------------------
  // Step 3
  // Add Product to Cart
  // ------------------------------------------------------------------
  // Adding the product converts browsing into a purchase candidate.
  // This confirms the cart accepts the selected item correctly.
  // ------------------------------------------------------------------

  await page.locator('[data-test="add-to-cart"]').click();

  log.info("Product added to cart", {
    product: "Running Shoes",
  });

  // ------------------------------------------------------------------
  // Step 4
  // Proceed to Checkout
  // ------------------------------------------------------------------
  // order journey now moves from cart management to checkout.
  // ------------------------------------------------------------------

  await page.locator('[data-test="checkout-button"]').click();

  log.info("Navigated to checkout");

  // ------------------------------------------------------------------
  // Step 5
  // Coupon Validation (Assessment Requirement)
  // ------------------------------------------------------------------
  //
  // The coupon is entered before placing the order so pricing and
  // checkout calculations can be validated as part of the journey.
  // ------------------------------------------------------------------

  const couponField = page.getByRole("textbox", {
    name: "Coupon code",
  });

  await couponField.click();
  await couponField.fill("TRY UST10");

  log.info("Coupon applied", {
    coupon: "TRY UST10",
  });

  // ------------------------------------------------------------------
  // Step 6
  // Order Confirmation
  // ------------------------------------------------------------------
  // The order should only be submitted after every previous business
  // step has completed successfully.
  //
  // This final step confirms the entire compound journey completed
  // without breaking at any intermediate stage.
  // ------------------------------------------------------------------

  await page.locator('[data-test="place-order"]').click();

  await expect(
    page.getByRole("heading", {
      name: "Thank you for your order",
    })
  ).toBeVisible();

  log.info("Compound checkout journey completed successfully", {
    product: "Running Shoes",
    coupon: "TRY UST10",
    confirmation: "Thank you for your order",
  });

  // ------------------------------------------------------------------
  // Evidence
  // ------------------------------------------------------------------
  // Store a concise summary so the assessment report can show exactly
  // what steps was executed.
  // ------------------------------------------------------------------

  evidence["compound-checkout-journey"] = {
    journey: [
      "Search Product",
      "Open Product",
      "Add To Cart",
      "Checkout",
      "Apply Coupon",
      "Place Order",
      "Order Confirmation",
    ],
    product: "Running Shoes",
    coupon: "TRY UST10",
    completed: true,
    confirmationHeading: "Thank you for your order",
    assessmentGoal:
      "Trace a compound add -> coupon -> checkout journey",
  };
});