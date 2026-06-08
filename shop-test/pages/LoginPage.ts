import { expect, type Page } from "@playwright/test";

export class LoginPage{
    constructor(private readonly page: Page){}

    async open(){
        await this.page.goto('/login');
    }

    async login(email:string, password: string){
        // var lognPage= await this.page.goto('/login');
            
        await this.page.getByLabel("Email").fill(email);
        await this.page.getByLabel("Password").fill(password);
        await this.page.getByLabel("Country").selectOption({index: 1})
        await this.page.getByRole("button", {name:"Sign in"}).click();
    }

    async expectError(message:string){
        await expect(this.page.getByRole("alert")).toContainText(message);
    }

}
