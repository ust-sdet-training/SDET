import {test, expect} from "../fixtures/diagnostic-test";

// In this test checking the flakiness in the confirmation page.
// Adding timeout after clicking the place order button in checkout page.
test("flakiness in confirmation page", async ({page, log}) => {
    log.info("Flakiness check")
    await page.goto("/catalog")
    await page.getByRole('link', { name: 'View Running Shoes' }).click();
    await page.getByRole('button', { name: 'Add to cart' }).click();
    await page.getByRole('button', { name: 'Proceed to checkout' }).click();
    await page.locator('#coupon-code').fill("UST10");
    await expect(page.locator('.order-summary')).toContainText('-Rs. 450');
    page.getByRole('button', { name: 'Place order' }).click({timeout:100});
    await expect(page.locator("#confirmation-title")).toHaveText("Thank you for your order")

    await page.screenshot({
        path: `artifacts/flaky.png`,
        fullPage: true
    })
})