import { test, expect } from "@playwright/test";

test("Assert the banner when in offline", async ({ page, context }) => {
 
    // Open the POS page and wait for the application to get loaded.
  await page.goto("/pos", { waitUntil: "domcontentloaded" });

  await expect(page.getByTestId("network-banner")).toBeVisible();

  // Confirm the page starts in the online mode before turning to offline mode.
  const setOnline = await page.evaluate(() => navigator.onLine);
  expect(setOnline).toBe(true);
  //Assert the banner shows the correct online state when the page is loaded.
  await expect(page.getByTestId("network-banner")).toContainText("Online");

  // Set to offline mode and verify the banner.
  await context.setOffline(true);
  //Assert the banner shows the correct offline state when the network is disconnected.
  await expect(page.getByTestId("network-banner")).toContainText("Offline");

  //Verify that the network is offline.
  const isOnline = await page.evaluate(() => navigator.onLine);
  expect(isOnline).toBe(false);
  await expect(page.getByTestId("network-banner")).toBeVisible();

  // Restore connectivity and verify the banner returns to the online state.
  await context.setOffline(false);
  await expect(page.getByTestId("network-banner")).toContainText("Online");

//Verify the network is back to online or not.
  const restoredOnlineState = await page.evaluate(() => navigator.onLine);
  expect(restoredOnlineState).toBe(true);
});
