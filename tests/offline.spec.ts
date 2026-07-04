import { test, expect } from "../fixtures/app"

test("Offline flow for sale queue", async ({ page, context, log }) => {
    let attempts = 0;
    let successPost = 0;
    const keys = new Set<string>();

    await page.route("**/api/sales", async (route) => {
        if (route.request().method() !== "POST") {
            return route.continue();
        }
        attempts++;
        const key = route.request().headers()["idempotency-key"];
        log.info("POST intercepted", {
            attempt: attempts,
            idempotencyKey: key
        });
        expect(key).toBeTruthy();
        keys.add(key!);
        if (attempts === 1) {
            log.warn("Simulating failure on first sync attempt");
            await route.abort("failed");
        } else {
            successPost++;
            log.info("Allowing retry to succeed");
            await route.continue();
        }
    });
    log.info("Navigating to POS page");

    await page.goto("/pos");
    await context.setOffline(true);
    log.info("Network switched off");
    await context.setOffline(true);

    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale while offline");
    /*
        Added this to make sure the outbox count increases
    */
    await expect(page.getByTestId("outbox-count")).toHaveText("1");

    await context.setOffline(false);

    log.info("Network restored");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified outbox flushed");

    await expect(attempts).toBe(2);
    await expect(successPost).toBe(1);

    expect(keys.size).toBe(1);

    log.info("Exactly-once delivery verified", {
        attempts,
        successPost,
        uniqueIdempotencyKeys: keys.size
    });
})