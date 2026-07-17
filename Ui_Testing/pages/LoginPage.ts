import {Page} from '@playwright/test'
export class LoginPage
{
    constructor (public readonly page:Page)
    {

    }
    async openLoginPage()
    {
        await this.page.goto("/login")
    }
    async login(username:string,password:string)
    {
        await this.page.getByRole("textbox",{name:"Email"}).fill(username)
        await this.page.getByRole("textbox",{name:"Password"}).fill(password)
        await this.page.getByRole('button', { name: 'Sign in' }).click();
    }
}