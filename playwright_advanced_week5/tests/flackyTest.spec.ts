import { test, expect } from "@playwright/test";
 
test("Intentional Flaky Demo", async ({ page }) => {
  await page.goto("/pos");
 
  //  chance of failure
  if (Math.random() < 0.5) {
    expect(false).toBe(true);
  }
 
  await expect(
    page.getByRole("button", { name: "Queue sale" })
  ).toBeVisible();
});
 