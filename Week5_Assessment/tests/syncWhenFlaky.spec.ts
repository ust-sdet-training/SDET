import { test, expect } from "../fixtures/diagnostics";

// Test to ensure that queued sales are synced exactly once, even when the network connection is flaky and causes retries.
test("Sync once, even through a flaky reconnect", async ({ page, context, log }) => {
  // Set up the request interception so the first POST is forced to fail.
  let attempts = 0;
  let successPosts = 0;
  const keys = new Set<string>();

  //Navigate to the sales page and intercept the POST /api/sales requests to simulate a flaky network.
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
      successPosts++;
      log.info("Allowing retry to succeed");
      await route.continue();
    }
  });

  // Open the POS page and confirm the queue action is available.
  await page.goto("/pos");
  log.info("Opened POS page");

  await expect(page.getByRole("button", { name: "Queue sale" })).toBeVisible();

  // Disconnect the browser to offline and queue a sale.
  await context.setOffline(true);
  log.info("Network switched offline");

  //Click the Queue sale button to queue a sale while offline
  await page.getByRole("button", { name: "Queue sale" }).click();
  await page.getByRole("button", { name: "Queue sale" }).click();
  await page.getByRole("button", { name: "Queue sale" }).click();

  log.info("Queued sale while offline");

  //Assert the outbox count is 3 after the queued sale
  await expect(page.getByTestId("outbox-count")).toHaveText("3");

  // Reconnect to network and verify the queued sale is synced
  await context.setOffline(false);
  log.info("Network restored");

  //Assert the outbox count is 0 after the queued sale is synced
  await expect(page.getByTestId("outbox-count")).toBeVisible();
  await expect(page.getByTestId("outbox-count")).toHaveText("0");
  log.info("Verified outbox flushed");

  // Confirm the retry logic worked exactly once.
  expect(attempts).toBe(4);
  expect(successPosts).toBe(1);
  expect(keys.size).toBe(1);

  log.info("Exactly-once delivery verified", {
    attempts,
    successPosts,
    uniqueIdempotencyKeys: keys.size
  });
});