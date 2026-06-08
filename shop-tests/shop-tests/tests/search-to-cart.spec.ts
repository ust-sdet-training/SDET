import { test, expect } from "@playwright/test";
import { ShopFlow } from "../flows/shopFlow";
import { LoginPage } from "../pages/LoginPage";
import { testUsers } from "../fixtures/test";

test("search to cart flow", async ({ page }) => {
  const loginPage = new LoginPage(page);

  await loginPage.goto();

  await loginPage.login(testUsers.customer.email, testUsers.customer.password);

  const shopFlow = new ShopFlow(page);

  await shopFlow.searchAndAddToCart("Running Shoes");
  await page
    .getByRole("button", {
      name: "Proceed to checkout",
    })
    .click();

  await expect(page).toHaveURL(/\/checkout$/);

  await expect(page.getByRole("button", { name: "Place Order" })).toBeVisible();
  await page.getByRole("button", { name: "Place Order" }).click();
  await expect(
    page.getByRole("heading", { name: "Thank you for your order" }),
  ).toBeVisible();
  await expect(page.getByRole("button", { name: "View orders" })).toBeVisible();
  await page.getByRole("button", { name: "View orders" }).click();
  await expect(page).toHaveURL(/\/orders$/);
});
