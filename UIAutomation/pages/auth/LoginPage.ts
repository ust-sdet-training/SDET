import { Page } from '@playwright/test';

export class LoginPage {

    constructor(
        private page: Page
    ) {}

    async open() {

        await this.page.goto(
            'https://tripstack.doomple.com/'
        );

        await this.page
            .getByRole(
                'link',
                { name: 'Log in' }
            )
            .click();
    }

    async login(
        email: string,
        password: string
    ) {

        await this.page
            .getByRole(
                'textbox',
                { name: 'Email' }
            )
            .fill(email);

        await this.page
            .getByRole(
                'textbox',
                { name: 'Password' }
            )
            .fill(password);

        await this.page
            .getByRole(
                'button',
                { name: 'Sign in' }
            )
            .click();
    }
}