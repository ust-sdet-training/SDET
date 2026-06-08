import { expect, type Page } from "@playwright/test";

export class CartPage {
    private proceedCheckoutButton = ()=>this.page.getByRole('button',{name:"proceed to checkout"})
constructor(private readonly page: Page) {}

    async proceedToCheckout(){

        const response = this.page.waitForResponse( (response)=>{

        return response.url().includes('/cart') && 
                response.status()===200 
             })

        await this.proceedCheckoutButton().click()
        
        await response
    }
}