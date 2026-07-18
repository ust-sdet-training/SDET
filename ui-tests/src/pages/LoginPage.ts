import { Page } from '@playwright/test';
import { Locators } from '@locators/locators';

export class LoginPage {
  private readonly locators: Locators;

  constructor(private readonly page: Page) {
    this.locators = new Locators(page);
  }

  async openLoginPage() {
    await this.page.goto('/login');
  }

  async login(email: string, password: string) {
    await this.locators.emailInput().fill(email);
    await this.locators.passwordInput().fill(password);
    await this.locators.signInBtn().click();
  }
}
