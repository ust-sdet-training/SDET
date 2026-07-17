import { Page } from "@playwright/test";

export class LoginLocators {

    constructor(private page: Page) {}

    usernameInput = () => this.page.getByLabel("Email");

    passwordInput = () => this.page.getByLabel("Password");

    loginButton = () => this.page.getByRole("button", { name: "Sign in" });

}