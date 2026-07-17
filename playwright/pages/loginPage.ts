import {Page} from "@playwright/test"

export class LoginPage{

    constructor(private readonly page:Page){}

    async gotoLogin(){
        await this.page.goto("/login");
    }

    async enterEmail(email:string){
        await this.page.getByLabel("email").isVisible();
        await this.page.getByLabel("email").fill(email);
    }

    async enterPassword(password:string){
        await this.page.getByLabel("password").isVisible();
        await this.page.getByLabel("password").fill(password);
    }

    async signIn(){
        await this.page.getByRole("button",{name:"Sign in"}).click();
    }
}