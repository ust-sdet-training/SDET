import { test, expect } from "@playwright/test";

const SALE_API = "**/api/sales";
const SALE_ID = "sale-123";

test.describe("Offline Webkit POS Synchronization", () => {

    test.beforeEach(async ({ browserName }) => {
        test.skip(
            browserName !== "webkit",
            "Offline sync scenarios are verified in WebKit only."
        );
    });

    test("shows offline banner when the browser loses connection", async ({ page, context, }) => {
        // open POS while online
        await page.goto("/pos");

        // set offline mode
        await context.setOffline(true);

        // asserting offline banner
        await expect(page.getByTestId("network-banner")).toContainText("Offline");

        // set back online
        await context.setOffline(false);

        // Offline banner disappears
        await expect(page.getByTestId("network-banner")).not.toContainText("Offline");
    });

    test("rolls back optimistic sale when sync fails", async ({ page }) => {
        // mock apifailure
        await page.route(SALE_API, async route => {

            await page.waitForTimeout(250);

            await route.abort();

        });

        await page.goto("/pos");

        // queue a sale
        await page.getByRole("button", { name: "Queue sale", }).click();

        // queued sale should disappear after rollback
        await expect(page.getByRole("row", { name: "Running Shoes", })).toHaveCount(0);

        //asserting rollback message
        await expect(page.getByTestId("pos-sync-status")).toContainText("Sync failed. Optimistic sale rolled back.");
    });

    test("syncs queued sale after reconnecting", async ({ page, context, }) => {

        let totalRequests = 0;
        let successfulSyncs = 0;

        const idempotencyKeys = new Set<string>();

        await page.route(SALE_API, async route => {

            totalRequests++;

            const key = await route.request().headerValue(
                "Idempotency-Key"
            );

            expect(key).toBeTruthy();

            idempotencyKeys.add(key!);

            // first time fails
            if (totalRequests === 1) {
                await route.abort();
                return;
            }

            successfulSyncs++;

            await route.fulfill({
                status: 201,
                contentType: "application/json",
                body: JSON.stringify({
                    saleId: SALE_ID,
                }),
            });

        });

        await page.goto("/pos");

        // queue sale while offline
        await context.setOffline(true);

        await page.getByRole("button", { name: "Queue sale", }).click();

        await expect(page.getByTestId("outbox-count")).toHaveText("1");

        // set online
        await context.setOffline(false);

        // assert automactic sync 
        await expect(page.getByTestId("outbox-count")).toHaveText("0");

        // assert retries
        expect(totalRequests).toBe(2);
        expect(successfulSyncs).toBe(1);

        // assert same key
        expect(idempotencyKeys.size).toBe(1);

    });

});