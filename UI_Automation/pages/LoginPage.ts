import {Page, Locator} from '@playwright/test';

export class LoginPage{

    constructor(private readonly page : Page){}

    inputEmail = () : Locator => this.page.getByRole("textbox", {name: "Email"});
    inputPassword = () : Locator => this.page.getByRole("textbox", {name: "Password"});
    
    logInButton = () : Locator => this.page.getByRole("button", {name: "Sign in"});


    async enterEmail(email: string){
        await this.inputEmail().fill(email);
    }

    async enterPassword(password: string){
        await this.inputPassword().fill(password);
    }

    async clickLogIn(){
        await this.logInButton().click();
    }



}
