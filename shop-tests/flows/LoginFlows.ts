import { Page, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

export class LoginFlow {
    private readonly loginPage: LoginPage;
    constructor(private readonly page: Page) {
        this.loginPage = new LoginPage(page);
    }

    async validLogin(email: string, password: string) {
        await this.loginPage.goto();
        await this.loginPage.login(email, password);
        await expect.soft(this.page).toHaveURL(/\/home/);
        await expect.soft(this.page.getByRole("heading", {name: /welcome/i})).toBeVisible();
    }

    async invalidLogin(email: string, password: string) {
        await this.loginPage.goto();
        await this.loginPage.login(email, password);
        await expect.soft(this.page).toHaveURL(/\/login/);
        await this.loginPage.expectError("Invalid credentials");
    }

    async lockedLogin(email: string, password: string) {
        await this.loginPage.goto();
        await this.loginPage.login(email, password);
        await expect.soft(this.page).toHaveURL(/\/login/);
        await this.loginPage.expectError("Account is locked");
    }
}