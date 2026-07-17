import { Page } from '@playwright/test';
import { BasePage } from './basePage';

export class LoginPage extends BasePage {
  constructor(page: Page) {
    super(page);
  }

  private readonly loginLink = () => this.page.getByRole('link', { name: /log in/i }).first();
  private readonly emailBox = () => this.page.getByLabel(/email/i).first();
  private readonly passwordBox = () => this.page.getByLabel(/password/i).first();
  private readonly signInButton = () => this.page.getByRole('button', { name: /sign in|log in/i }).first();
  private readonly errorBanner = () => this.page.getByRole('alert').filter({ hasText: /invalid|error/i }).first();

  async open(): Promise<void> {
    await this.navigate();
  }

  async login(email: string, password: string): Promise<void> {
    await this.loginLink().click();
    await this.emailBox().fill(email);
    await this.passwordBox().fill(password);
    await this.signInButton().click();
  }

  errorMessage() {
    return this.errorBanner();
  }
}
