import { test, expect } from "@playwright/test";

test("Offline flow test", async ({ page, context }) => {

  let syncRequests = 0;

  // mocking /api/sales endpoint 
  await page.route("**/api/sales", async route => {
    if (route.request().method() !== "POST") {
      return route.continue();
    }

    syncRequests++;

    await route.fulfill({
      status: 201,
      contentType: "application/json",
      body: JSON.stringify({
        saleId: "sale-123",
      }),
    });
  });

  // opening POS page
  await page.goto("http://localhost:5173/pos");

  // going offline
  await context.setOffline(true);

  // asserting offline message
  await expect(page.getByText("Offline - sales will be queued")).toBeVisible();

  // queueing a sale 
  await page.getByRole("button", { name: "Queue sale" }).click();

  // asserting request count while offiline
  expect(syncRequests).toBe(0);

  // going back online
  await context.setOffline(false);

  // asserting online status
  await expect(page.evaluate(() => navigator.onLine)).resolves.toBe(true);

  // syncing queued sales
  await page.getByRole("button", { name: "Sync now" }).click();

  // asserting only one sync request was sent
  expect(syncRequests).toBe(1);

});