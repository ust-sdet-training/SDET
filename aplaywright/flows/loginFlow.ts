import { Page,expect } from "@playwright/test";
import { LoginPage } from "../pages/loginPage";

export class Loginflow{
    private readonly loginPage:LoginPage;

    constructor(private readonly page:Page){
        this.loginPage = new LoginPage(page);
    }

    async ValidLogin(email:string,password:string){
        await this.loginPage.goto();
        await this.loginPage.login(email,password);
        await expect.soft(this.page).toHaveURL(/\/home/);
        await expect.soft(this.page.getByRole('heading',{name: /welcome/i})).toBeVisible();
    }

    async inValidLogin(email:string,password:string){
        await this.loginPage.goto();
        await this.loginPage.login(email,password);
        await expect.soft(this.page).toHaveURL(/\/login/);
        await this.loginPage.errorMessage("Invalid");
    }

    async lockedLogin(email:string,password:string){
        await this.loginPage.goto();
        await this.loginPage.login(email,password);
        await expect.soft(this.page).toHaveURL(/\/login/);
        await this.loginPage.errorMessage("locked");
    }
    

}