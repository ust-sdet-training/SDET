import { expect, test } from "../fixtures/diagnostic-test";

test("queued sale retries with same idempotency key", async ({
  page,
  context
}) => {
  let attempts = 0;
  let successfulPosts = 0;
  const keys = new Set<string>();

  // Intercept sales API calls to simulate a retry
  await page.route("**/api/sales", async (route) => {
    if (route.request().method() !== "POST") {
      return route.continue();
    }

    attempts++;

    const key = route.request().headers()["idempotency-key"];

    expect(key).toBeTruthy();

    if (key) {
      keys.add(key);
    }

    // Fail the first request
    if (attempts === 1) {
      return route.abort("failed");
    }

    successfulPosts++;

    await route.fulfill({
      status: 201,
      contentType: "application/json",
      body: JSON.stringify({
        saleId: "sale-123"
      })
    });
  });

  await page.goto("/pos");

  // Simulate offline mode
  await context.setOffline(true);

  await page.getByRole("button", {
    name: "Queue sale"
  }).click();

  await expect(
    page.getByTestId("outbox-count")
  ).toHaveText("1");

  await context.setOffline(false);

  await expect(
    page.getByTestId("outbox-count")
  ).toHaveText("0");

  // Ensure only one successful sale is created
  expect(attempts).toBe(2);
  expect(successfulPosts).toBe(1);
  expect(keys.size).toBe(1);
});