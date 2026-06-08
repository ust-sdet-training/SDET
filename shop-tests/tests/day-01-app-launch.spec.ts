import { expect, test } from "@playwright/test";

test.describe("Day 1 - App Launch & Validation", () => {
  test("launches the retail app and validates the landing page", async ({ page }) => {
    await page.goto("/");

    await expect(page).toHaveTitle(/SDET Retail Automation Lab/);
    await expect(page).toHaveURL(/localhost|127\.0\.0\.1/);
    await expect(
      page.getByRole("heading", { name: "SDET Retail Automation Lab", level: 1 })
    ).toBeVisible();
    const primaryNavigation = page.getByRole("navigation", { name: "Primary navigation" });
    await expect(primaryNavigation).toBeVisible();
    await expect(primaryNavigation.getByRole("link", { name: "Products" })).toBeVisible();

    await page.screenshot({
      path: "test-results/day-01-app-launch.png",
      fullPage: true
    });
  });

  test("shows the Day 1 automation checklist", async ({ page }) => {
    await page.goto("/home");

    await expect(page.getByRole("heading", { name: "Automation Checks" })).toBeVisible();
    await expect(page.getByText("Validate browser title")).toBeVisible();
    await expect(page.getByText("Capture a full-page screenshot")).toBeVisible();
  });
});
