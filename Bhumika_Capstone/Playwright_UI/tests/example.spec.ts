import { test, expect } from "../fixtures/baseFixture";

test("TripStack home page loads", async ({ page }) => {
  await page.goto("/");
  await expect(page).toHaveTitle(/TripStack/i);
});
