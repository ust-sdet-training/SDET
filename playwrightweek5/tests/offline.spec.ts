import { expect, test } from "@playwright/test";
test("prove the offline banner", async ({ page, context }) => {
  //first we are going to the page
  await page.goto("/pos");
  // then we are making it offline
  await context.setOffline(true);

  //adding assertions after system went to offline what we can able to see in the application
  await expect(page.getByTestId("network-banner")).toHaveText(
    /Offline - sales will be queued/i,
  );

  await expect(page.getByTestId("net-state")).toHaveAttribute(
    "data-online",
    "false",
  );
});
