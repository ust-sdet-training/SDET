import { expect, type Page } from "@playwright/test";

export class LoginPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto("/login");
  }

  async login(email: string, password: string) {
    const Loginform = this.page.getByRole('form', {name: 'Login'})
    await Loginform.getByLabel("Email").fill(email);
    await Loginform.getByLabel("Password").fill(password);
    await Loginform.locator(".button primary form-submit").click();
  }

  async expectError(message: string) {
    await expect(this.page.getByRole("alert")).toContainText(message);
  }
}
