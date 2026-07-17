import {expect,type Page} from "@playwright/test"

export class LoginPage{

    constructor(private page:Page){}

    async goto(){
        await this.page.goto("https://tripstack.doomple.com/");
    }

    async login(username:string,password:string){
        await this.page.getByRole('link', { name: 'Log in' }).click();
        await this.page.getByRole('textbox', { name: 'Email' }).click();
        await expect(this.page.getByRole('textbox', { name: 'Email' })).toBeEmpty();
        await this.page.getByRole('textbox', { name: 'Email' }).fill('peggy@tripstack.test');
        await this.page.getByRole('textbox', { name: 'Password' }).click();
        await this.page.getByRole('textbox', { name: 'Password' }).fill('Password@123');
        await this.page.getByRole('button', { name: 'Sign in' }).click();
        
    }

    async errorMessage(){
        return this.page.locator("#error");
    }
}
