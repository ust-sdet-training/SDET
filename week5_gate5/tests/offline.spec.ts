import { expect, test } from "@playwright/test";

test("prove the offline banner", async ({ page, context }) => {
  await page.goto("/pos");

  // Simulate loss of network
  await context.setOffline(true);

  await expect(page.getByTestId("network-banner"))
    .toHaveText(/Offline - sales will be queued/i);

  // Verify the app detects offline mode
  await expect(page.getByTestId("net-state"))
    .toHaveAttribute("data-online", "false");
});