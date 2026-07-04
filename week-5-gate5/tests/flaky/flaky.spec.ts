import {test, expect} from "../../fixtures/dignostic.fixture";


// Verify checkout flow remains stable when navigating to the confirmation page
test("flaky", async ({page, log}) => {
    log.info("Flakiness check")
    await page.goto("/catalog")
    await page.getByRole('link', { name: 'View Running Shoes' }).click();
    await page.getByRole('button', { name: 'Add to cart' }).click();
    await page.getByRole('button', { name: 'Proceed to checkout' }).click();
    await page.locator('#coupon-code').fill("UST10");
    await expect(page.locator('.order-summary')).toContainText('-Rs. 450');
    // Allow a little extra time for order submission
    await page.getByRole('button', { name: 'Place order' }).click({timeout:100});
    await expect(page.locator("#confirmation-title")).toHaveText("Thank you for your order")
})