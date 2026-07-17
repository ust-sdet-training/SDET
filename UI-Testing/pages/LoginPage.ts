import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class LoginPage extends BasePage {
    readonly email: Locator
    readonly password: Locator
    readonly signInBtn: Locator
    // readonly nextSuggestion: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log);

        this.email = page.getByLabel("Email")
        this.password = page.getByLabel("Password")
        this.signInBtn = page.getByRole("button", {name: "Sign in"})
    }

    async signIn(email: string, password: string): Promise<void> {
        await expect(this.isVisible(this.signInBtn))
        await this.fill(this.email, email)
        await this.fill(this.password, password)
        await this.click(this.signInBtn)
    }
}