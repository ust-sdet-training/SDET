import { test, expect } from "../../fixtures/app.fixture";

test.describe("Making Network Offline and Online", () => {

  test("Prove the offline banner", async ({ page, context, log, evidence }) => {

    // Open the POS page before simulating the network change.
    log.info("Opening POS page");
    await page.goto("/pos");

    // Switch the browser to offline mode.
    log.info("Switching network to offline");
    await context.setOffline(true);

    // Verify the offline banner appears in the UI.
    await expect(page.getByTestId("network-banner")).toContainText("Offline");
    log.info("Verified offline banner");

    // Confirm the browser reports that it is offline.
    expect(await page.evaluate(() => navigator.onLine)).toBe(false);
    log.info("Verified navigator.onLine is false");

    // Restore the network and check that the banner updates.
    log.info("Restoring network");
    await context.setOffline(false);

    await expect(page.getByTestId("network-banner")).toContainText("Online");
    log.info("Verified online banner");

    // Go offline again so sales can be queued locally.
    log.info("Switching offline again to queue sales");
    await context.setOffline(true);

    // Queue a sale while the app is offline.
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 1");

    // Queue a second sale to build up the outbox.
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 2");

    // Queue a third sale to confirm the outbox count grows.
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 3");

    // Verify the queued sales are stored locally.
    await expect(page.getByTestId("outbox-count")).toHaveText("3");
    log.info("Verified outbox contains 3 queued sales");

    // Reconnect the network to sync the queued sales.
    log.info("Reconnecting network");
    await context.setOffline(false);

    // Confirm the outbox is cleared after syncing.
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified queued sales synced successfully");

    evidence.cartResponse = {
      queuedSales: 3,
      synced: true,
    };
  });


  test("Rollback a failed optimistic write", async ({ page, log, evidence }) => {

    // Intercept the sale API call to simulate a network failure.
    log.info("Intercepting POST /api/sales");

    await page.route("**/api/sales", async (route) => {
      log.warn("Simulating failed network request");

      await new Promise(resolve => setTimeout(resolve, 250));

      await route.abort("failed");
    });

    // Open the POS page and try to queue a sale.
    await page.goto("/pos");
    log.info("Opened POS page");

    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale");

    // Verify the sale is shown as pending before the sync fails.
    await expect(page.getByTestId("outbox-status")).toHaveText("pending");
    log.info("Verified optimistic pending state");

    // Confirm the failed write is rolled back from the outbox.
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified rollback removed queued sale");

    // Assert that the UI reports the rollback failure message.
    await expect(page.getByTestId("pos-sync-status"))
      .toHaveText("Sync failed. Optimistic sale rolled back.");

    log.info("Sync failed and UI is rolled back");

    evidence.cartResponse = {
      rollback: true,
    };
  });


  test("sync once, even through a flaky reconnect", async ({ page, context, log, evidence }) => {

    // Track the number of submit attempts and successful retries.
    let attempts = 0;
    let successPosts = 0;
    const keys = new Set<string>();

    // Intercept sales requests to simulate a flaky reconnect and verify idempotency.
    await page.route("**/api/sales", async (route) => {

      if (route.request().method() !== "POST") {
        return route.continue();
      }

      attempts++;

      // Read the idempotency key attached to the request.
      const key = route.request().headers()["idempotency-key"];

      log.info("POST intercepted", {
        attempt: attempts,
        idempotencyKey: key
      });

      // Ensure each request carries an idempotency key.
      expect(key).toBeTruthy();
      keys.add(key!);

      if (attempts === 1) {
        log.warn("Simulating failure on first sync attempt");
        await route.abort("failed");
      } else {
        successPosts++;
        log.info("Allowing retry to succeed");
        await route.continue();
      }
    });

    // Open the POS page and go offline before queueing the sale.
    await page.goto("/pos");
    log.info("Opened POS page");

    await context.setOffline(true);
    log.info("Network switched offline");

    // Queue a sale while the connection is down.
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale while offline");

    // Restore the connection so the queued sale can be retried.
    await context.setOffline(false);
    log.info("Network restored");

    // Verify the outbox is flushed after the retry succeeds.
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified outbox flushed");

    expect(attempts).toBe(2);
    expect(successPosts).toBe(1);
    expect(keys.size).toBe(1);

    log.info("Exactly-once delivery verified", {
      attempts,
      successPosts,
      uniqueIdempotencyKeys: keys.size
    });

    evidence.cartResponse = {
      attempts,
      successPosts,
      uniqueIdempotencyKeys: keys.size
    };
  });

});