import {Page} from '@playwright/test'

export class Bookpage
{
    constructor(public readonly page:Page){}


    async bookit(email:string,phone:string)
    {
       

        await this.page.getByRole('textbox',{name:/Email/}).fill(email)
        await this.page.getByRole('textbox',{name:/Phone/}).fill(phone)

        await this.page.getByRole('button',{name:/Continue/}).click()


    }   
}
