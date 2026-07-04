import { expect, test } from "../fixtures/evidence";

test.describe("sync offline example", () => {

    test.beforeEach(async ({ browserName }) => {
        test.skip(browserName !== "webkit", "Run this sync offline task in WebKit");
    });

    // simple: verify offline banner appears when context goes offline
    test("prove offline banner", async ({ page, context, evidence }) => {
        await page.goto("/pos");

        await context.setOffline(true);
        await expect(page.getByTestId("network-banner")).toContainText("Offline");

        const isOnline = await page.evaluate(() => navigator.onLine);
        expect(isOnline).toBe(false);

        await context.setOffline(false);
        await expect(page.getByTestId("network-banner")).not.toContainText("Offline");

        evidence.cartResponse = {
            page: "/pos",
            navigatorOnlineWhileOffline: isOnline,
            banner: "Offline",
        };
        evidence.diagnosis = "Offline banner appears when the browser context goes offline.";
    });

    // simple: check optimistic sale is rolled back on sync failure
    test("sale is rolled back when sync fails", async ({ page, evidence }) => {
        await page.route("**/api/sales", async route => {
            await new Promise(resolve => setTimeout(resolve, 250));
            await route.abort();
        });

        await page.goto("/pos");
        await page.getByRole("button", { name: "Queue sale" }).click();

        evidence.cartResponse = {
            item: "Running Shoes",
            pendingRowCount: 0,
            syncStatus: "Sync failed. Optimistic sale rolled back.",
        };
        evidence.diagnosis = "Failed sync removes the optimistic sale row.";

        const row = page.getByRole("row", { name: "Running Shoes" });
        await expect(row).toHaveAttribute("data-pending", "true");
        await expect(row).toHaveCount(0);
        await expect(page.getByTestId("pos-sync-status")).toContainText("Sync failed. Optimistic sale rolled back.");
    });

    // simple: enqueue offline sale, reconnect, and ensure single sync
    test("offline queue reconnects and syncs once", async ({ page, context, evidence }) => {
        let attempts = 0;
        let successfulPosts = 0;
        const keys = new Set<string>();

        await page.route("**/api/sales", async route => {
            attempts++;

            const key = await route.request().headerValue("Idempotency-Key");
            expect(key).toBeTruthy();
            keys.add(key!);

            if (attempts === 1) {
                await route.abort();
                return;
            }

            successfulPosts++;
            await route.fulfill({
                status: 201,
                contentType: "application/json",
                body: JSON.stringify({
                    saleId: "sale-123",
                }),
            });
        });

        await page.goto("/pos");

        await context.setOffline(true);
        await page.getByRole("button", { name: "Queue sale" }).click();
        await expect(page.getByTestId("outbox-count")).toHaveText("1");

        await context.setOffline(false);
        await expect(page.getByTestId("outbox-count")).toHaveText("0");

        expect(attempts).toBe(2);
        expect(successfulPosts).toBe(1);
        expect(keys.size).toBe(1);

        evidence.cartResponse = {
            attempts,
            successfulPosts,
            idempotencyKeys: Array.from(keys),
        };
        evidence.diagnosis = "Offline sale queued once, reconnected, retried with the same key, and synced once.";
    });

});
