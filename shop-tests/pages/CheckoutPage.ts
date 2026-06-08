import { expect, type Page } from "@playwright/test";

export class CheckoutPage {
    private placeOrderButton = ()=>this.page.getByRole('button',{name:"place order"})
    private getneworderid = ()=>this.page.locator('p').filter({hasText:"confirmed"})
    private viewOrderButton = ()=>this.page.getByRole('button',{name:"view order"})

     private static neworderid : string

    


    constructor(private readonly page: Page) {}

    async placeOrder(){
        await this.placeOrderButton().click()

        CheckoutPage.neworderid = await this.getneworderid().textContent() as string

        await this.viewOrderButton().click()

        

        
        
    }
    async checkorderid(orderid:string){    

        await expect(CheckoutPage.neworderid).toContain(orderid)
        
    }
}