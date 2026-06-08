import { test, expect } from "@playwright/test";
import { testUsers } from "../fixtures/test";

test("invalid product search shows no results", async ({ page }) => {
  await page.goto("/catalog");

  await page.locator("#search-products").fill("evyse");

  await page.getByRole("button", { name: "Search" }).click();

  await expect(page.getByText("No products found")).toBeVisible();
});
test("invalid login credentials", async ({ page }) => {
  await page.goto("/login");
  const loginForm = page.getByRole("form", { name: "Login" });

  await loginForm.getByLabel("Email").fill("invalid@gmail.com");
  await loginForm.getByLabel("Password").fill("WrongPassword123");
  await loginForm.getByRole("button", { name: "Sign in" }).click();

  await expect(page).toHaveURL(/\/login$/);
  await expect(page.getByRole("alert")).toContainText(/invalid credentials/i);
});
