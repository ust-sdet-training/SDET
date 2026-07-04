import { test, expect } from "../fixtures/diagnostic.fixture";

test("Offline flow for sale queue", async ({ page, context, log }) => {

    // Initialize counters to track API requests
    let attempts = 0;
    let successPost = 0;
    // Store unique idempotency keys
    const keys = new Set<string>();
    // Intercept requests sent to the sales API
    await page.route("**/api/sales", async (route) => {
        // Allow non-POST requests to continue
        if (route.request().method() !== "POST") {
            return route.continue();
        }
        attempts++;
        // Read the idempotency key from the request
        const key = route.request().headers()["idempotency-key"];
        
        log.info("POST intercepted", {
            attempt: attempts,
            idempotencyKey: key
        });
        expect(key).toBeTruthy();
        // Save the key for later verification
        keys.add(key!);

        // Fail the first request intentionally
        if (attempts === 1) {
            log.warn("Simulating failure on first sync attempt");
            await route.abort("failed");
        } else {
            // Allow the retry request to succeed
            successPost++;
            log.info("Allowing retry to succeed");
            await route.continue();
        }
    });

    log.info("Navigating to POS page");
    await page.goto("/pos");
    // Simulate offline mode
    await context.setOffline(true);
    log.info("Network switched off");

    // Queue a sale while offline
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale while offline");
    // Restore the network connection
    await context.setOffline(false);
    log.info("Network restored");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified outbox flushed");

    // Verify the API was called twice
    expect(attempts).toBe(2);
    // Verify only one request was successful
    expect(successPost).toBe(1);
    // Verify the same idempotency key was reused
    expect(keys.size).toBe(1);
    // Log the final verification results
    log.info("Exactly-once delivery verified", {
        attempts,
        successPost,
        uniqueIdempotencyKeys: keys.size
    });
});