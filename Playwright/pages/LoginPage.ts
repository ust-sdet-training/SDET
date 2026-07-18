import { Page } from "@playwright/test";
import { LoginLocators } from "../locators/LoginLocators";
import { logger } from "../logger/logger";

export class LoginPage {

    private readonly page: Page;
    private readonly loginLocators: LoginLocators;

    constructor(page: Page) {
        this.page = page;
        this.loginLocators = new LoginLocators(page);
    }

    async navigate(url:string){
        logger.info(`Navigating to ${url}`);
        await this.page.goto(url);
    }

    async openLoginPage(){
        logger.info("Opening login page");
        await this.loginLocators.loginLink.click();
    }
    
    async enterEmail(email: string){
        logger.info("Entering email address");
        await this.loginLocators.email.fill(email);
    }

    async enterPassword(password: string){
        logger.info("Entering password");
        await this.loginLocators.password.fill(password);
    }

    async clickSignIn(){
        logger.info("Clicking sign-in button");
        await this.loginLocators.loginButton.click();
    }

    async login(email: string, password: string){
        logger.info("Starting login flow");
        await this.enterEmail(email);
        await this.enterPassword(password);
        await this.clickSignIn();
        logger.info("Login flow completed");
    }
}