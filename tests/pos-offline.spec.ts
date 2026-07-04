import { test, expect } from "../fixtures/test";

const API_BASE_URL = process.env.POS_API_URL || "http://localhost:4000";
const AUTH_HEADERS = {
  Authorization: "Bearer demo-token-1-customer"
};

test.describe("Week 5 POS stabilization", () => {
  test("offline POS sale queues while offline, reconnects once, and publishes evidence", async ({
    context,
    evidence,
    page,
    request,
    log
  }) => {
    log.info("Starting offline POS stabilization test");

    const saleSession = `gate-pos-${Date.now()}`;

    const salesBeforeResponse = await request.get(`${API_BASE_URL}/api/sales`, {
      headers: { ...AUTH_HEADERS, "X-Cart-Session": saleSession }
    });
    expect(salesBeforeResponse.status()).toBe(200);
    await evidence.attachJson("sales-before.json", await salesBeforeResponse.json());

    let salesPostCount = 0;
    const seenIdempotencyKeys = new Set<string>();

    await page.route("**/api/sales", async (route) => {
      salesPostCount += 1;
      const idempotencyKey = route.request().headers()["idempotency-key"];
      expect(idempotencyKey).toBeTruthy();
      seenIdempotencyKeys.add(idempotencyKey);
      await route.continue({
        headers: {
          ...route.request().headers(),
          "x-cart-session": saleSession
        }
      });
    });

    await page.goto("/pos");
    await page.getByRole("button", { name: "Reset lab" }).click();

    await context.setOffline(true);
    await expect(page.getByTestId("network-banner")).toContainText("Offline");
    await page.getByRole("button", { name: "Queue sale" }).click();
    await expect(page.getByTestId("pos-sync-status")).toContainText("queued locally while offline");
    await expect(page.getByTestId("outbox-count")).toHaveText("1");

    const outboxContents = await page.evaluate(() =>
      JSON.parse(window.localStorage.getItem("sdet-retail-pos-outbox") || "[]")
    );
    await evidence.attachJson("offline-outbox.json", outboxContents);
    expect(outboxContents).toHaveLength(1);
    expect(outboxContents[0].queuedWhileOnline).toBe(false);

    await context.setOffline(false);
    await expect(page.getByTestId("network-banner")).toContainText("Online");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    await expect(page.getByTestId("last-synced-sale")).toContainText("SALE-");

    expect(salesPostCount).toBe(1);
    expect(seenIdempotencyKeys.size).toBe(1);

    const salesAfterResponse = await request.get(`${API_BASE_URL}/api/sales`, {
      headers: { ...AUTH_HEADERS, "X-Cart-Session": saleSession }
    });
    expect(salesAfterResponse.status()).toBe(200);
    const salesLedger = await salesAfterResponse.json();
    await evidence.attachJson("sales-after.json", salesLedger);

    expect(salesLedger.items).toHaveLength(1);
    expect(salesLedger.items[0]).toMatchObject({
      productName: "Running Shoes",
      quantity: 1,
      status: "SYNCED"
    });
  });
});