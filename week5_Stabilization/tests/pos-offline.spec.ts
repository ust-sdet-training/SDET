import { test, expect } from "../fixtures/test";

const apiBaseUrl = process.env.POS_API_URL || "http://localhost:4000";
const authHeaders = {
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
    const salesBefore = await request.get(`${apiBaseUrl}/api/sales`, {
      headers: { ...authHeaders, "X-Cart-Session": saleSession }
    });
    expect(salesBefore.status()).toBe(200);
    await evidence.attachJson("sales-before.json", await salesBefore.json());

    let postCount = 0;
    const idempotencyKeys = new Set<string>();

    await page.route("**/api/sales", async (route) => {
      postCount += 1;
      const key = route.request().headers()["idempotency-key"];
      expect(key).toBeTruthy();
      idempotencyKeys.add(key);
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

    const queuedOutbox = await page.evaluate(() =>
      JSON.parse(window.localStorage.getItem("sdet-retail-pos-outbox") || "[]")
    );
    await evidence.attachJson("offline-outbox.json", queuedOutbox);
    expect(queuedOutbox).toHaveLength(1);
    expect(queuedOutbox[0].queuedWhileOnline).toBe(false);

    await context.setOffline(false);
    await expect(page.getByTestId("network-banner")).toContainText("Online");
    await expect(page.getByTestId("outbox-count")).toHaveText("0");
    await expect(page.getByTestId("last-synced-sale")).toContainText("SALE-");

    expect(postCount).toBe(1);
    expect(idempotencyKeys.size).toBe(1);

    const salesAfter = await request.get(`${apiBaseUrl}/api/sales`, {
      headers: { ...authHeaders, "X-Cart-Session": saleSession }
    });
    expect(salesAfter.status()).toBe(200);
    const salesLedger = await salesAfter.json();
    await evidence.attachJson("sales-after.json", salesLedger);

    expect(salesLedger.items).toHaveLength(1);
    expect(salesLedger.items[0]).toMatchObject({
      productName: "Running Shoes",
      quantity: 1,
      status: "SYNCED"
    });
  });
});
