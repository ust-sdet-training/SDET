import { expect, type Page } from "@playwright/test";
import { Logger } from "../src/utils/Logger";

export class LoginPage {

    constructor(private page: Page){}

    async goto(){

        Logger.info("Opening TripStack Website");

        await this.page.goto("https://tripstack.doomple.com/");

        Logger.success("Website Opened");
    }

    async login(username:string,password:string){

        Logger.info("Clicking Login Button");

        await this.page.getByRole('link', { name: 'Log in' }).click();

        Logger.info("Entering Email");

        await this.page.getByRole('textbox', { name: 'Email' }).fill(username);

        Logger.info("Entering Password");

        await this.page.getByRole('textbox', { name: 'Password' }).fill(password);

        Logger.info("Clicking Sign In");

        await this.page.getByRole('button', { name: 'Sign in' }).click();

        Logger.success("Login Successful");
    }

    async errorMessage(){
        return this.page.locator("#error");
    }

}