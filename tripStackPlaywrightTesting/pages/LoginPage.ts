import { expect, Locator, Page } from '@playwright/test';

export class LoginPage {

    readonly page: Page;

    readonly loginLink: Locator;
    readonly emailTextbox: Locator;
    readonly passwordTextbox: Locator;
    readonly signInButton: Locator;

    constructor(page: Page) {
        this.page = page;

        this.loginLink = page.getByRole('link', { name: 'Log in' });

        this.emailTextbox = page.getByRole('textbox', {
            name: 'Email'
        });

        this.passwordTextbox = page.getByRole('textbox', {
            name: 'Password'
        });

        this.signInButton = page.getByRole('button', {
            name: 'Sign in'
        });
    }

    async navigate() {
        await this.page.goto('/');
    }

    async openLogin() {
        await this.loginLink.click();
    }

    async verifyLoginPageLoaded() {
        await expect(this.emailTextbox).toBeVisible();
        await expect(this.passwordTextbox).toBeVisible();
        await expect(this.signInButton).toBeVisible();
    }

    async enterEmail(email: string) {
        await this.emailTextbox.fill(email);
    }

    async enterPassword(password: string) {
        await this.passwordTextbox.fill(password);
    }

    async clickSignIn() {
        await this.signInButton.click();
    }

    async login(email: string, password: string) {
        await this.enterEmail(email);
        await this.enterPassword(password);
        await this.clickSignIn();
    }

}