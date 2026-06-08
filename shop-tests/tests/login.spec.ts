import { expect, test } from "@playwright/test";
import { testUsers } from "../fixtures/test-users";

test.describe("Week 1 Day 2 - Login Workflow", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/login");
  });

  test("login form controls are visible and located by user-facing locators", async ({ page }) => {
    const loginForm = page.getByRole("form", { name: "Login" });

    await expect(loginForm.getByLabel("Email")).toBeVisible();
    await expect(loginForm.getByLabel("Password")).toBeVisible();
    await expect(loginForm.getByLabel("Country")).toBeVisible();
    await expect(loginForm.getByLabel("Remember me")).toBeVisible();
    await expect(loginForm.getByRole("button", { name: "Sign in" })).toBeVisible();
  });

  test("valid customer can sign in", async ({ page }) => {
    const loginForm = page.getByRole("form", { name: "Login" });

    await loginForm.getByLabel("Email").fill(testUsers.customer.email);
    await loginForm.getByLabel("Password").fill(testUsers.customer.password);
    await loginForm.getByLabel("Remember me").check();
    await loginForm.getByLabel("Country").selectOption({ label: "India" });
    await loginForm.getByRole("button", { name: "Sign in" }).click();

    await expect(page).toHaveURL(/\/home$/);
    await expect(
      page.getByRole("heading", { name: `Welcome, ${testUsers.customer.displayName}` })
    ).toBeVisible();
    await expect(page.getByRole("button", { name: "Sign out" })).toBeVisible();
  });

  test("invalid login shows a visible error", async ({ page }) => {
    const loginForm = page.getByRole("form", { name: "Login" });

    await loginForm.getByLabel("Email").fill(testUsers.invalid.email);
    await loginForm.getByLabel("Password").fill(testUsers.invalid.password);
    await loginForm.getByRole("button", { name: "Sign in" }).click();

    await expect(page).toHaveURL(/\/login$/);
    await expect(page.getByRole("alert")).toContainText(/invalid credentials/i);
    await expect(page.getByRole("heading", { name: /welcome/i })).not.toBeVisible();
  });

  test("locked user sees account locked message", async ({ page }) => {
    const loginForm = page.getByRole("form", { name: "Login" });

    await loginForm.getByLabel("Email").fill(testUsers.locked.email);
    await loginForm.getByLabel("Password").fill(testUsers.locked.password);
    await loginForm.getByRole("button", { name: "Sign in" }).click();

    await expect(page.getByRole("alert")).toContainText(/account is locked/i);
  });
});
