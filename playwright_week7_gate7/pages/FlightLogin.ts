import {Page} from '@playwright/test'

export class FlightLoginpage
{
    constructor(public readonly page:Page){}

     async home()
    {
        await this.page.goto("/login")
    }

    async flightLogin(email:string,password:string)
    {
        await this.page.getByRole('textbox',{name:'Email'}).fill(email)
        await this.page.getByRole('textbox',{name:'Password'}).fill(password)
        await this.page.getByRole('button', { name: 'Sign in' }).click();

    }   

    async invalidEmail(invalidemail:string,password:string)
    {
        await this.page.getByRole('textbox',{name:'Email'}).fill(invalidemail)
        await this.page.getByRole('textbox',{name:'Password'}).fill(password)
        await this.page.getByRole('button', { name: 'Sign in' }).click();

    }  
    
     async invalidPass(email:string,invalidpassword:string)
    {
        await this.page.getByRole('textbox',{name:'Email'}).fill(email)
        await this.page.getByRole('textbox',{name:'Password'}).fill(invalidpassword)
        await this.page.getByRole('button', { name: 'Sign in' }).click();

    }  
    
}
 