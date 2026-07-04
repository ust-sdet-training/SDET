import {test, expect} from "@playwright/test"


// Flakiness is introduced by using waitForTimeout after the "sign in" button is clicked. Removing the timeouts results in green test without flakiness
test("flaky login test",async ({page}) =>{
    await page.goto('/login')
    await page.getByLabel("Email").fill("customer@example.com")
    await page.getByLabel("Password").fill("Password@123");
    
    await page.getByRole("button", {name: "Sign in"}).click()

    await page.waitForTimeout(100);

    await expect(page).toHaveURL(/home/, { timeout: 50 })
});


// Flakiness is introduced by asserting cart total before the async request is sent. Removing timeouts removes the flakiness
test("asserts cart total before the async request", async ({ page }) => {
    await page.goto("/debug-lab");
    await page.getByRole("button", { name: "Refresh cart total" }).click();
    await expect(page.getByTestId("debug-cart-total")).toHaveText("Rs. 9,197", {timeout: 150});
  });


// Using brittle locators such as ".product-grid article:nth-child(2) a" results in flakiness. using stable locators removes the flakiness
test("brittle product locator", async ({ page }) => {
    await page.goto("/catalog");
    await page.locator(".product-grid article:nth-child(2) a").click();
    await expect(page.getByRole("heading", { name: "Travel Backpack" })).toBeVisible();
  });


// flakiness is introduced by not awaiting for "Place order" button click. Awaiting removes the flakiness
test("Apply coupon - flaky", async ({ page }) => {
  await page.goto("/catalog");

  await page.getByRole("link", { name: "View Running Shoes" }).click();
  await page.getByRole("button", { name: "Add to cart" }).click();
  await page.getByRole("button", { name: "Proceed to checkout" }).click();

  await page.locator("#coupon-code").fill("UST10");
  await expect(page.locator(".order-summary")).toContainText("-Rs. 450");

  page.getByRole("button", { name: "Place order" }).click();
  await expect( page.getByRole("button", { name: "View orders" })).toBeVisible({ timeout: 300 });
});


// Flakiness introduced by freezing the time
test('Freeze browser time', async ({ page }) => {
  await page.clock.install();
  await page.clock.setFixedTime(new Date('2025-01-01T10:00:00'));
  await page.goto('/catalog');

  await expect(page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
});





