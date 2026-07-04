import { expect, test } from "@playwright/test";
import { testUsers } from "../fixtures/test";
test("valid customer can sign in", async ({ page }) => {
  await page.goto("/login");
  const loginForm = page.getByRole("form", { name: "Login" });

  await loginForm.getByLabel("Email").fill(testUsers.customer.email);
  await loginForm.getByLabel("Password").fill(testUsers.customer.password);
  await loginForm.getByLabel("Remember me").check();
  await loginForm.getByLabel("Country").selectOption({ label: "India" });

  await loginForm.getByRole("button", { name: "Sign in" }).click();

  await expect(page).toHaveURL(/\/home$/);

  await expect(page.getByRole("button", { name: "Sign out" })).toBeVisible();
});
