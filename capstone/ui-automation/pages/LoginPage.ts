import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";

export class LoginPage extends BasePage {

    readonly loginLink: Locator;
    readonly emailTextbox: Locator;
    readonly passwordTextbox: Locator;
    readonly signInButton: Locator;

    constructor(page: Page) {

        super(page);

        this.loginLink = page.getByRole("link", { name: "Log in" });
        this.emailTextbox = page.getByRole("textbox", { name: "Email" });
        this.passwordTextbox = page.getByRole("textbox", { name: "Password" });
        this.signInButton = page.getByRole("button", { name: "Sign in" });
    }

    async login(email: string, password: string) {
        logger.info("[LoginPage] Starting login flow");
        await this.click(this.loginLink, "Login link");
        await this.fill(this.emailTextbox, email, "email");
        await this.fill(this.passwordTextbox, password, "password");
        await this.click(this.signInButton, "Sign in button");
        logger.info("[LoginPage] Login submitted");
    }

}