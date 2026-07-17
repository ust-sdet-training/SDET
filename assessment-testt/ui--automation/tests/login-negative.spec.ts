import { test, expect } from "./fixtures";
import { ENV } from "../src/config/env";
import { LoginPage } from "../src/pages/login.page";

test("invalid login should stay on the login page", async ({ page }) => {
  const loginPage = new LoginPage(page);

  await page.goto(ENV.baseUrl);
  await page.getByRole("link", { name: "Log in" }).click();

  const attemptedUrl = page.url();
  await loginPage.login("invalid.user@test.local", "wrong-password");

  await expect(loginPage.signInButton).toBeVisible({ timeout: 5000 });
  await expect(page).toHaveURL(attemptedUrl);

  const errorMessage = page.locator("text=/invalid|incorrect|wrong|failed/i").first();
  if (await errorMessage.count()) {
    await expect(errorMessage).toBeVisible({ timeout: 5000 });
  }
});
