import { expect, test } from "../fixtures/diagnostic-test";

test("Adding logs for the checkout journey", async ({page, log}) => {
    log.info("Checkout journey")
    await page.goto("/catalog")
    await page.getByRole('link', { name: 'View Running Shoes' }).click();
    log.info("Opened Product Detail Page")
    await page.getByRole('button', { name: 'Add to cart' }).click();
    log.info("cart page opened")
    await page.getByRole('button', { name: 'Proceed to checkout' }).click();
    log.info("checkout page opened")
    await page.locator('#coupon-code').fill("UST10");
    await expect(page.locator('.order-summary')).toContainText('-Rs. 450');
    log.info("coupon applied")
    await page.getByRole('button', { name: 'Place order' }).click();
    await expect(page.locator("#confirmation-title")).toHaveText("Thank you for your order")
    log.info("order placed")
})