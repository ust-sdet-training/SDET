import {expect,Page} from "@playwright/test"
import {Confirmpage} from '../pages/ConfirmPage'

export class Confirmflows{

    readonly conp : Confirmpage

    constructor(public readonly page:Page){

        this.conp = new Confirmpage(page)

    }

    async confirm(cardName:string,card:string,expiry:string,cvv:string)
    {

        await expect(this.page).toHaveURL(/payment/)
        await expect(this.page.getByRole('heading', {name:/Secure checkout/, level:1})).toBeVisible()
        await expect(this.page.getByRole('heading', {name:/checkout/, level:1})).toBeVisible()

        await this.conp.confirmation(cardName,card,expiry,cvv)
       

        
    }


}