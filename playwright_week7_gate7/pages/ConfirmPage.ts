import {Page} from '@playwright/test'

export class Confirmpage
{
    constructor(public readonly page:Page){}


    async confirmation(cardName:string,card:string,expiry:string,cvv:string)
    {

         await this.page.getByRole('textbox',{name:/Name/}).fill(cardName)
        await this.page.getByRole('textbox',{name:/Card/}).fill(card)

        await this.page.getByRole('textbox',{name:/Expiry/}).fill(expiry)
        await this.page.getByRole('textbox',{name:/CVV/}).fill(cvv)

        await this.page.getByRole('button',{name:/Pay/}).click()
    }   
}
