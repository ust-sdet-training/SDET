import { Page } from "@playwright/test";
import { Config } from "../config/Config";
import { LoginLocators } from "../locators/LoginLocators";
import { logger } from "../logger/Logger";

export class LoginPage {
  private locators: LoginLocators;

  constructor(private page: Page) {
    this.locators = new LoginLocators(page);
  }

  async open() {
    logger.info("Opening login page");
    await this.page.goto("/login");
  }

  async enterUsername(username: string) {
    const field = this.locators.emailInput();
    await field.waitFor({ state: "visible" });
    await field.fill(username);
  }

  async enterPassword(password: string) {
    const field = this.locators.passwordInput();
    await field.waitFor({ state: "visible" });
    await field.fill(password);
  }

  async clickLogin() {
    const button = this.locators.loginButton();
    await button.waitFor({ state: "visible" });
    logger.info("Clicking login button");
    await button.click();
  }

  async login(username: string, password: string) {
    logger.info(`Logging in user ${username}`);
    await this.enterUsername(username);
    await this.enterPassword(password);
    await this.clickLogin();
  }
}
