import { expect, Locator, Page } from "@playwright/test";

export class LoginPage {
    private loginLink: Locator;
    private emailTextbox: Locator;
    private passwordTextbox: Locator;
    private signInButton: Locator;
    constructor(private readonly page: Page) {
        this.loginLink = page.getByRole("link", { name: "Log in" });
        this.emailTextbox = page.getByRole("textbox", { name: "Email" });
        this.passwordTextbox = page.getByRole("textbox", { name: "Password" });
        this.signInButton = page.getByRole("button", { name: "Sign in" });
    }
    async login(email: string, password: string) {
        await this.loginLink.click();
        await this.emailTextbox.fill(email);
        await this.passwordTextbox.fill(password);
        await this.signInButton.click();
        await expect(
            this.page.getByRole("heading", {
                name: "Book flights & buses across",
            })
        ).toBeVisible();
    }
}