import { test, expect } from "../fixtures/evidence";

test("Gate 5 stabilization assessment for resilient POS queueing", async ({ page, context, log, evidence }) => {
  // This baseline matters because the test must prove the POS page is healthy before it introduces any offline transition.
  log.info("Opening the POS page in a controlled baseline state", { targetUrl: "http://localhost:5173/pos" });

  await page.goto("http://localhost:5173/pos");

  await page.evaluate(() => {
    window.localStorage.removeItem("sdet-retail-pos-outbox");
    window.sessionStorage.setItem(
      "sdet-retail-user",
      JSON.stringify({
        email: "customer@example.com",
        name: "Customer User",
        role: "customer",
        token: "demo-token-1-customer",
      })
    );
    window.localStorage.setItem(
      "sdet-retail-pos-outbox",
      JSON.stringify([
        {
          clientSaleId: "seed-sale-001",
          productName: "Running Shoes",
          status: "synced",
          total: 4499,
          queuedWhileOnline: true,
          queuedAt: new Date().toISOString(),
        },
      ])
    );
  });

  await page.reload();

  // This checkpoint exists because the online banner and pending count are the first signals that the resilience flow is starting from a correct state.
  await expect(page.getByTestId("network-banner")).toContainText("Online");
  await expect(page.getByTestId("outbox-count")).toHaveText("0");

  const seededSaleId = "seed-sale-001";
  await expect(page.locator("tbody tr").filter({ hasText: seededSaleId }).getByTestId("outbox-status")).toHaveText("synced");

  log.info("Verified the POS start state is online with a seeded synced outbox record", {
    bannerText: await page.getByTestId("network-banner").textContent(),
    outboxCount: await page.getByTestId("outbox-count").textContent(),
  });

  // This queue action button to prove that an online sale is immediately synchronized and does not leave a stale pending record behind.
  await page.getByRole("button", { name: "Queue sale" }).click();

  const onlineQueuedSaleId = await page.evaluate(() => {
    const outbox = JSON.parse(window.localStorage.getItem("sdet-retail-pos-outbox") || "[]");
    return outbox[0]?.clientSaleId ?? null;
  });

  await expect(page.getByTestId("outbox-count")).toHaveText("0");
  await expect(page.getByTestId("network-banner")).toContainText("Online");
  await expect(page.locator("tbody tr").filter({ hasText: onlineQueuedSaleId || "" }).getByTestId("outbox-status")).toHaveText("synced");

  log.info("Verified an online queue is synchronized immediately without leaving pending work", {
    queuedSaleId: onlineQueuedSaleId,
  });

  // This offline transition is the heart of the test because it forces the app to persist the sale locally instead of syncing it.
  await context.setOffline(true);
  await expect(page.getByTestId("network-banner")).toContainText("Offline");
  await expect(page.getByRole("button", { name: "Queue sale" })).toBeEnabled();

  // This offline queue step matters because the pending count and pending status are the business proof that the sale is being preserved for later synchronization.
  await page.getByRole("button", { name: "Queue sale" }).click();

  const offlineQueuedSaleId = await page.evaluate(() => {
    const outbox = JSON.parse(window.localStorage.getItem("sdet-retail-pos-outbox") || "[]");
    return outbox[0]?.clientSaleId ?? null;
  });

  await expect(page.getByTestId("outbox-count")).toHaveText("1");
  await expect(page.locator("tbody tr").filter({ hasText: offlineQueuedSaleId || "" }).getByTestId("outbox-status")).toHaveText("pending");
  await expect(page.locator("tbody tr").filter({ hasText: seededSaleId }).getByTestId("outbox-status")).toHaveText("synced");

  log.info("Verified the offline queue is persisted as pending while the seeded synced record remains intact", {
    pendingSaleId: offlineQueuedSaleId,
    seededSaleId,
  });

  let syncCount = 0;
  await page.route("**/api/sales", async (route) => {
    syncCount += 1;
    await route.continue();
  });

  // Restoring connectivity is the final validation point because it should trigger exactly one sync and clear the pending outbox without duplication.
  await context.setOffline(false);
  await expect(page.getByTestId("network-banner")).toContainText("Online");
  await expect(page.getByTestId("outbox-count")).toHaveText("0");
  await expect(page.locator("tbody tr").filter({ hasText: offlineQueuedSaleId || "" }).getByTestId("outbox-status")).toHaveText("synced");
  expect(syncCount).toBe(1);

  log.info("Verified the queued sale was synced exactly once after connectivity was restored", {
    syncCount,
    syncedSaleId: offlineQueuedSaleId,
  });

  evidence["pos-resilience-flow"] = {
    initialBanner: await page.getByTestId("network-banner").textContent(),
    initialOutboxCount: await page.getByTestId("outbox-count").textContent(),
    onlineQueuedSaleId,
    offlineQueuedSaleId,
    syncCount,
  };
});
