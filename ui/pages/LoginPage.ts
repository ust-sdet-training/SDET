import { expect } from '@playwright/test';
import { APP_BASE_URL, APP_PATHS, DEFAULT_TIMEOUTS } from '../constants';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
  async goto() {
    this.logger?.info('Login step started');
    await this.page.goto(`${APP_BASE_URL}${APP_PATHS.login}`, { waitUntil: 'domcontentloaded' });
  }

  async login(email: string, password: string) {
    await this.page.getByLabel('Email').fill(email);
    await this.page.getByLabel('Password').fill(password);
    await this.page.getByRole('button', { name: /sign in/i }).click();

    const logoutLink = this.page.getByRole('link', { name: 'Log out' });
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
