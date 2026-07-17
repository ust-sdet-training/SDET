import { expect, type Locator, type Page } from '@playwright/test';
import { env } from '../support/env';

export class LoginPage {
  readonly email: Locator;
  readonly password: Locator;
  readonly submit: Locator;

  constructor(private readonly page: Page) {
    this.email = page.getByLabel('Email');
    this.password = page.getByLabel('Password');
    this.submit = page.getByRole('button', { name: 'Sign in' });
  }

  async goto(): Promise<void> {
    await this.page.goto('/login');
    await expect(this.submit).toBeVisible();
  }

  async login(): Promise<void> {
    const credentials = env.credentials();
    await this.loginWith(credentials.email, credentials.password);
    await expect(this.page).not.toHaveURL(/\/login/);
  }

  async loginWith(email: string, password: string): Promise<void> {
    await this.email.fill(email);
    await this.password.fill(password);
    await this.submit.click();
  }
}
