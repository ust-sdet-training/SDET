import { test, expect } from "@playwright/test";

test(
  "stable checkout flow with coupon and offline recovery",

  async ({ page }) => {
    // STEP 1 — OPEN CATALOG
    await page.goto("/catalog");

    // STEP 2 — SEARCH PRODUCT
    await page.fill("#search-products", "headphone");
    await page.click("[data-test=\"search-button\"]");
    await expect(page.locator("[data-test=\"product-card\"]")).toBeVisible();

    // STEP 3 — OPEN PRODUCT
    await page.click("[data-test=\"product-card\"] a");
    await expect(page).toHaveURL(/product/);

    // STEP 4 — ADD TO CART
    await page.click("[data-test=\"add-to-cart\"]");

    // STEP 5 — OPEN CART
    await page.goto("/cart");
    await expect(page.locator("[data-test=\"cart-page\"]")).toBeVisible();

    // STEP 6 — CHECKOUT
    await page.click("[data-test=\"checkout-button\"]");
    await expect(page).toHaveURL(/checkout/);

    // STEP 7 — APPLY COUPON
    await page.fill("#coupon-code", "UST10");

    // STEP 8 — OFFLINE BEFORE ORDER
    await page.context().setOffline(true);
    expect(await page.evaluate(() => navigator.onLine)).toBe(false);

    // STEP 9 — RECONNECT
    await page.context().setOffline(false);
    expect(await page.evaluate(() => navigator.onLine)).toBe(true);

    // STEP 10 — PLACE ORDER
    await page.click("[data-test=\"place-order\"]");

    // STEP 11 — VERIFY CONFIRMATION
    await expect(page.locator("[data-test=\"order-confirmation\"]")).toBeVisible();

    // STEP 12 — VIEW ORDER
    await page.locator("[data-test=\"order-confirmation\"] button").click();
  }
);
