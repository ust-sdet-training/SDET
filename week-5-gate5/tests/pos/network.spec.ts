import { test, expect } from "../../fixtures/app.fixture";


test.describe("Making Network Offline and Online", () => {
  test("Prove the offline banner", async ({ page, context, log, evidence }) => {
    log.info("Opening POS page");
    await page.goto("/pos");
    // Simulate losing connectivity
    log.info("Switching network to offline");
    await context.setOffline(true);
    await expect(page.getByTestId("network-banner")).toContainText("Offline");
    log.info("Verified offline banner");
    expect(await page.evaluate(() => navigator.onLine)).toBe(false);
    log.info("Verified navigator.onLine is false");
    log.info("Restoring network");
    await context.setOffline(false);
    await expect(page.getByTestId("network-banner")).toContainText("Online");
    log.info("Verified online banner");
    // Queue sales while offline
    log.info("Switching offline again to queue sales");
    await context.setOffline(true);
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 1");
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 2");
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale 3");
    await expect(page.getByTestId("outbox-count")).toHaveText("3");
    log.info("Verified outbox contains 3 queued sales");
    // Reconnect and let queued sales sync
    log.info("Reconnecting network");
    await context.setOffline(false);
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified queued sales synced successfully");
    evidence.cartResponse = {
      queuedSales: 3,
      synced: true,
    };
  });



  test("Rollback a failed optimistic write", async ({ page, log, evidence }) => {
    // Force sync requests to fail
    log.info("Intercepting POST /api/sales");
    await page.route("**/api/sales", async (route) => {
      log.warn("Simulating failed network request");
      await new Promise(resolve => setTimeout(resolve, 250));
      await route.abort("failed");
    });
    await page.goto("/pos");
    log.info("Opened POS page");
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale");
    // Optimistic update appears before sync completes
    await expect(page.getByTestId("outbox-status")).toHaveText("pending");
    log.info("Verified optimistic pending state");
    // Failed sync should rollback the change
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified rollback removed queued sale");
    await expect(page.getByTestId("pos-sync-status")).toHaveText("Sync failed. Optimistic sale rolled back.");
    log.info("Sync failed and UI is rolled back");
    evidence.cartResponse = {
      rollback: true,
    };
  });


  test("Sync once, even through a flaky reconnect", async ({
    page,
    context,
    log,
    evidence,
  }) => {
    let attempts = 0;
    let successPosts = 0;
    const keys = new Set<string>();
    await page.route("**/api/sales", async (route) => {
      if (route.request().method() !== "POST") {
        return route.continue();
      }
      attempts++;
      const key = route.request().headers()["idempotency-key"];
      log.info("POST intercepted", {
        attempt: attempts,
        idempotencyKey: key,
      });
      expect(key).toBeTruthy();
      keys.add(key!);
      // Fail first attempt and allow the retry
      if (attempts === 1) {
        log.warn("Simulating failure on first sync attempt");
        await route.abort("failed");
      } else {
        successPosts++;
        log.info("Allowing retry to succeed");
        await route.continue();
      }
    });
    await page.goto("/pos");
    log.info("Opened POS page");
    await context.setOffline(true);
    log.info("Network switched offline");
    await page.getByRole("button", { name: "Queue sale" }).click();
    log.info("Queued sale while offline");
    // Reconnect to trigger sync
    await context.setOffline(false);
    log.info("Network restored");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    log.info("Verified outbox flushed");
    // Verify exactly-once delivery
    expect(attempts).toBe(2);
    expect(successPosts).toBe(1);
    expect(keys.size).toBe(1);
    evidence.cartResponse = {
      attempts,
      successPosts,
      uniqueIdempotencyKeys: keys.size,
    };
  });
});