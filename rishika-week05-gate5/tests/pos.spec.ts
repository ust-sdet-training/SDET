import { test, expect } from "../fixtures/app.fixture";

test("Offline sync - load page before going offline", async ({page,context,log,}) => {

  // Open POS while online
  await page.goto("/pos");
  // Wait until the page is fully loaded
  await expect(page.getByRole("heading", { name: "POS" })).toBeVisible();
  log.info("POS page loaded successfully");
  // Go offline only after the page has loaded
  await context.setOffline(true);
  // Verify offline banner is shown
  await expect(page.getByTestId("network-banner")).toContainText('Offline - sales will be queued');
  log.info("Application switched to offline mode");
  // Go back online
  await context.setOffline(false);
  await expect(page.getByTestId("network-banner")).toContainText('Online - sales sync normally');
  log.info("Application reconnected successfully");
});