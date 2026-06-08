import { test, expect } from "@playwright/test";

test("catalog page basic accessibility checks", async ({ page }) => {
  await page.goto("/catalog");

  await expect(page.getByRole("searchbox")).toBeVisible();

  await expect(
    page.getByRole("button", { name: "Search" })
  ).toBeVisible();

  await expect(page.getByRole("navigation")).toBeVisible();
});