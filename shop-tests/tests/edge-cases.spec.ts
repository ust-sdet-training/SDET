import { test, expect } from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";


test("search for invalid product shows no results", async ({ page }) => {
  await page.goto("/catalog");

  await page.locator("#search-products").fill("SuperFakeProduct123");

  await page.getByRole("button", { name: "Search" }).click();

  await expect(page.getByText("No products found")).toBeVisible();
});


test("login with invalid email shows validation error", async ({ page }) => {
  await page.goto("/login");

  await page.getByLabel("Email").fill("invalid-email");
  await page.getByLabel("Password").fill("Password123");

  await page.getByRole("button", { name: "Sign in" }).click();

  await expect(
    page.getByText("Please enter a valid email")
  ).toBeVisible();
});


test("axe accessibility scan - catalog page", async ({ page }) => {
  await page.goto("/catalog");

  const results = await new AxeBuilder({ page }).analyze();

  expect(results.violations).toEqual([]);
});