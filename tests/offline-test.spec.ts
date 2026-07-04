import { expect, test } from "../fixtures/diagnostic-test";

// Making the network offline, then trying to queue sale, it will go to pending state.
// When connected to network back, the sales sync automatically.
test("offline test and then making online and checking sync", async ({ page, context, log }) => {
    log.info("checking for how UI interacts while making sale in offline")
    await page.goto("/pos");
    await page.reload()
    log.info("Network set to offline")
    await context.setOffline(true);
    await expect(page.getByText("Offline - sales will be queued")).toBeVisible();
    await expect(navigator.onLine).toBeFalsy()
    const online = await page.evaluate(() => navigator.onLine)
    expect(online).toBe(false)
    log.info("making a sale")
    await page.getByRole("button", {name: "Queue sale"}).click()
    log.info("sale is in pending state when network is not connected")
    await expect(page.getByTestId("outbox-count")).toHaveText("1")
    log.info("Connecting back to network")
    await context.setOffline(false)
    log.info("Sales automatically synced")
    await expect(page.getByText("Online - sales sync normally")).toBeVisible();
    await expect(page.getByTestId("outbox-count")).toHaveText("0")
});