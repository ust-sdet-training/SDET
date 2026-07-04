import { test, expect } from '../fixtures/pos';

test("verify the cart count after load click as flaky", async ({ page, log }) => {

    log.info("Navigating to Sync Lab page");
    await page.goto("/sync-lab");
    
    //access the load button 
    log.info("Clicking the Load button");
    await page.getByRole("button", { name: "Load" }).click();

    // Short timeout is intentional to demonstrate flaky behavior.
    log.warn("Using a short timeout to simulate a flaky test");


    //explicit wait for flaky 
    await expect(page.getByTestId("cart-count")).toHaveText("3", {
        timeout: 960,
    });
// log for verified count
    log.info("Verified cart count is 3");

    const cartCount = await page.getByTestId("cart-count").textContent();

     //if cartcount is not 3 then log error
    if (cartCount !== "3") {
        log.error("Cart count verification failed", {
            expected: "3",
            actual: cartCount,
        });
    }
});