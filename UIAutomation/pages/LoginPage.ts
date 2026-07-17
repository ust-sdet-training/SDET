import { Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../src/logger";

export class LoginPage extends BasePage {
  constructor(page: Page, log: AppLogger) {
    super(page, log);
  }

  readonly loginLink = this.page.getByRole("link", { name: "Log in" });

  readonly emailBox = this.page.getByRole("textbox", { name: "Email" });

  readonly passwordBox = this.page.getByRole("textbox", { name: "Password" });

  readonly signInButton = this.page.getByRole("button", { name: "Sign in" });

  async login(email: string, password: string) {
    this.log.info(`Logging in as: ${email}`);
    await this.click(this.loginLink);
    await this.click(this.emailBox);
    await this.fill(this.emailBox, email);
    await this.click(this.passwordBox);
    await this.fill(this.passwordBox, password);
    await this.click(this.signInButton);
  }
}
