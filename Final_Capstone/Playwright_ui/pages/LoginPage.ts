import { expect, Page } from '@playwright/test';
import { Logger } from '../src/logger/logger';

export class LoginPage {

    constructor(private page: Page) { }

    private loginLink = () =>
        this.page.getByRole('link', { name: 'Log in' });

    private emailTextbox = () =>
        this.page.getByRole('textbox', { name: 'Email' });

    private passwordTextbox = () =>
        this.page.getByRole('textbox', { name: 'Password' });

    private signInButton = () =>
        this.page.getByRole('button', { name: 'Sign in' });

    private busesLink = () =>
        this.page.getByRole('link', { name: 'Buses' });

    async navigate() {

        Logger.info("Opening TripStack");

        await this.page.goto('/');

    }

    async login(username: string, password: string) {

        Logger.info("Opening Login Page");

        await this.loginLink().click();

        Logger.info("Entering Username");

        await this.emailTextbox().fill(username);

        Logger.info("Entering Password");

        await this.passwordTextbox().fill(password);

        Logger.info("Clicking Sign In");

        await this.signInButton().click();

    }

    async verifyLogin() {

        await expect(this.busesLink()).toBeVisible();

        Logger.success("Login Successful");

    }

}