import { expect, test } from "@playwright/test";

test("test for product selection", async ({ page }) => {
  await page.goto("https://www.shoppersstop.com/");
  await page.getByRole("link", { name: "MEN", exact: true }).click();
  await page.getByRole("link", { name: "MEN", exact: true }).click();
  await page.getByText("T-Shirts", { exact: true }).click();
  const page1Promise = page.waitForEvent("popup");
  await page.getByRole("link", { name: "product card Puma Solid" }).click();
  const page1 = await page1Promise;
  await page1.getByRole("button", { name: "Add to bag" }).click();
  await page1.getByRole("img", { name: "shopping-cart" }).click();
});
test("test for fill and click positive scenario", async ({ page }) => {
  await page.goto("https://www.shoppersstop.com/");
  await expect(page).toHaveURL("https://www.shoppersstop.com/");
  await page.getByRole("img").nth(4).click();
  await page.getByPlaceholder("Enter your Mobile Number").click();
  await expect(page.getByPlaceholder("Enter your Mobile Number")).toBeVisible();
  await page.getByPlaceholder("Enter your Mobile Number").fill("9398127273");
  await expect(page.getByRole("button", { name: "Continue" })).toBeVisible();
  await page.getByRole("button", { name: "Continue" }).dblclick();
});
test("test for fill and click negative scenario", async ({ page }) => {
  await page.goto("https://www.shoppersstop.com/");
  await expect(page).toHaveURL("https://www.shoppersstop.com/");
  await page.getByRole("img").nth(4).click();
  await page.getByPlaceholder("Enter your Mobile Number").click();
  await expect(page.getByPlaceholder("Enter your Mobile Number")).toBeVisible();
  await page.getByPlaceholder("Enter your Mobile Number").fill("2348");
  await expect(page.getByText("Please enter a valid number.")).toBeVisible();
});
