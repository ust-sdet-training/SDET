import {expect,Page} from "@playwright/test"
import {Paymentpage} from '../pages/PaymentPage'

export class Paymentflows{

    readonly payp : Paymentpage

    constructor(public readonly page:Page){

        this.payp = new Paymentpage(page)

    }

    async pay(cardName:string,card:string,expiry:string,cvv:string)
    {
        
        await expect(this.page).toHaveURL(/payment/)
        await expect(this.page.getByRole('heading', {name:/checkout/, level:1})).toBeVisible()

        await this.payp.payment(cardName,card,expiry,cvv)
    }


}