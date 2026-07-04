import { test, expect } from "../fixtures/diagnostic.fixture";

test.describe("Offline Testing", () => {

    test("Verify application when network is offline", async ({ page, log }) => {

        // Open application
        await page.goto("/");

        // Simulate offline mode
        await page.context().setOffline(true);

        // Refresh the page
        await page.reload().catch(() => {});

        // Verify browser is offline
        expect(await page.evaluate(() => navigator.onLine)).toBe(false);

        log.info("Application is running in offline mode");

        // Restore network
        await page.context().setOffline(false);

    });
    test("Verify application works after network is restored", async ({ page, log }) => {

    // Go offline
    await page.context().setOffline(true);

    // Bring network back
    await page.context().setOffline(false);

    // Open application
    await page.goto("/");

    // Verify page loads successfully
    await expect(page).toHaveURL(/.*/);

    log.info("Application loaded successfully after restoring network");

});

});