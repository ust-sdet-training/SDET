import { expect } from '@playwright/test';
import { APP_BASE_URL, APP_PATHS, DEFAULT_TIMEOUTS } from '../constants';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
  async goto() {
    this.logger?.info('Login step started');
    await this.page.goto(`${APP_BASE_URL}${APP_PATHS.login}`, { waitUntil: 'domcontentloaded' });
  }

  async login(email: string, password: string) {
    const emailField = this.page.getByLabel(/email/i).or(this.page.locator('input[type="email"]')).first();
    const passwordField = this.page.getByLabel(/password/i).or(this.page.locator('input[type="password"]')).first();
    await emailField.fill(email);
    await passwordField.fill(password);
    await this.page.getByRole('button', { name: /sign in/i }).click();

    const logoutLink = this.page.getByRole('link', { name: /log out/i }).or(this.page.getByText(/log out/i));
    const errorAlert = this.page.getByRole('alert').filter({ hasText: /invalid|incorrect|email or password/i });

    await expect.poll(
      async () => {
        const hasLogout = await logoutLink.isVisible().catch(() => false);
        const hasError = await errorAlert.isVisible().catch(() => false);
        return hasLogout || hasError;
      },
      {
        timeout: DEFAULT_TIMEOUTS.medium,
        message: 'Expected either a successful login state or an invalid-login error message',
      }
    ).toBeTruthy();

    const loginSucceeded = await logoutLink.isVisible().catch(() => false);
    if (loginSucceeded) {
      this.logger?.info('Login completed');
      return true;
    }

    this.logger?.info('Login returned an error state');
    return false;
  }
}
