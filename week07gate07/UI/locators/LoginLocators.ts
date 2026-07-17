// locators/LoginLocators.ts

import { Locator, Page } from "@playwright/test";

export class LoginLocators {

    readonly emailTextBox: Locator;
    readonly passwordTextBox: Locator;
    readonly signInButton: Locator;
    readonly loginForm: Locator;
    readonly signInHeading: Locator;

    constructor(private readonly page: Page) {

        // Login Form
        this.loginForm = page.locator("[data-id='login-form']");

        // Email
        this.emailTextBox = page.locator("[data-id='login-email']");

        // Password
        this.passwordTextBox = page.locator("[data-id='login-password']");

        // Sign In Button
        this.signInButton = page.locator("[data-id='login-submit']");

        // Heading
        this.signInHeading = page.getByRole("heading", {
            name: "Sign in to TripStack"
        });

    }
}
