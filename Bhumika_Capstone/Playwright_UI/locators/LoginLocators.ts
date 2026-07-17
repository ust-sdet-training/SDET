import { Page, Locator } from "@playwright/test";

export class LoginLocators {
  constructor(private page: Page) {}

  emailInput(): Locator {
    return this.page.locator('input[name="email"]');
  }

  passwordInput(): Locator {
    return this.page.locator('input[name="password"]');
  }

  loginButton(): Locator {
    return this.page.locator('button[type="submit"]');
  }
}
