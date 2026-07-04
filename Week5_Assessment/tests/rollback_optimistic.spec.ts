import { test, expect } from "../fixtures/diagnostics";

test("Rollback a failed optimistic write", async ({ page, log }) => {

  //Here we are rolling back a failed optimistic to the original state
  // write by intercepting the sale submission request 
  log.info("Intercepting POST /api/sales");

  await page.route("**/api/sales", async (route) => {
  log.warn("Simulating failed network request");

    // Add a short delay before aborting to mimic a real failed request.
    await new Promise(resolve => setTimeout(resolve, 250));

    await route.abort("failed");
  });

  // Open the POS page where the queue action is available.
  await page.goto("/pos");
  log.info("Opened POS page");

  // Click the Queue sale multipel times for queuing a sale.
  await page.getByRole("button", { name: "Queue sale" }).click();
  log.info("Queued sale");

  // Verify the outbox shows the sale is still pending after the failure.
  await expect(page.getByTestId("outbox-status")).toHaveText("pending");
  log.info("Verified optimistic pending state");

  // Confirm the failed queued sale has been rolled back and removed.
  await expect(page.getByTestId("outbox-count")).toHaveText("0");
  log.info("Verified rollback removed queued sale");
});
 