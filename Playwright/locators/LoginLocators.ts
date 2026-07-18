import { Page, Locator } from "@playwright/test";

export class LoginLocators {

    readonly email: Locator;
    readonly password: Locator;
    readonly loginButton: Locator;
    readonly loginLink: Locator;

    constructor(page: Page) {

        this.loginLink = page.getByRole('link', { name: 'Log in' });
        
        this.email = page.getByRole('textbox', { name: 'Email' });

        this.password = page.getByRole('textbox', { name: 'Password' });

        this.loginButton = page.getByRole('button', { name: 'Sign in' });

    }

}