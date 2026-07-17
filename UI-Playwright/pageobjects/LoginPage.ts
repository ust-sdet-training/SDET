import { Locator, Page, expect } from "@playwright/test";

export class LoginPage {

    constructor(readonly page: Page) { }

    private emailTextBox = () : Locator => this.page.getByRole('textbox', { name: 'Email' });
    private passwordTextBox = () : Locator => this.page.getByRole('textbox', { name: 'Password' });
    private signInButton = () : Locator => this.page.getByRole('button', { name: 'Sign in' });

    async verifyLoginPageLoaded(){
        await expect(this.page).toHaveURL('/login');
        await expect(this.emailTextBox()).toBeVisible();
    }

    async login(email: string, password: string) {
        await this.emailTextBox().fill(email);
        await this.passwordTextBox().fill(password);
        await this.signInButton().click();
    }

}