import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }



    // Locators
    private loginLink = () => this.page.locator('a[href="/login"]');
    private email = () => this.page.locator('[data-id="login-email"]');
    private password = () => this.page.locator('[data-id="login-password"]');
    private signIn = () => this.page.locator('[data-id="login-submit"]');

    // Open Application
    async openApplication() {

        await this.page.goto('/');

    }

    // Open Login Page
    async openLoginPage() {

        await this.loginLink().click();

        await expect(this.page).toHaveURL(/login/);

    }

    // Login
    async login(email: string, password: string) {

        await this.email().fill(email);

        await this.password().fill(password);

        await this.signIn().click();

        await expect(this.page).not.toHaveURL(/login/);

    }

    // Complete Login Flow
    async loginToApplication() {

        await this.openApplication();

        await this.openLoginPage();

        await this.login(
            process.env.EMAIL!,
            process.env.PASSWORD!
        );

    }

}