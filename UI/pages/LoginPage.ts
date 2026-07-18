import { Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { LoginLocators } from "../locators/LoginLocators";
import { Config } from "../config/Config";

export class LoginPage extends BasePage {

    private readonly locators: LoginLocators;

    constructor(page: Page) {

        super(page);
        this.locators = new LoginLocators(page);
    }

   async navigateToLogin() {

    await this.page.goto(`${Config.baseUrl}login`);

    console.log("URL:", this.page.url());
    console.log("Title:", await this.page.title());

    await this.page.pause();

    await this.waitForPage();
}

    async login(email: string, password: string) {

    console.log("Current URL Before Login:", this.page.url());

    await this.fill(this.locators.emailTextBox, email);

    await this.fill(this.locators.passwordTextBox, password);

    await this.click(this.locators.signInButton);

    await this.waitForPage();
}

}