import {expect,Locator,Page} from '@playwright/test';
 
export class CartPage{
    constructor(private readonly page:Page){}
 
    checkOutButton =():Locator => this.page.getByRole('button',{name:"Proceed to checkout"});
 
    orderButton =():Locator => this.page.getByRole('button',{name:"Place Order"});
    async proceedToCheckout(){
        await this.checkOutButton().click();
    }
 
    async placeOrder(){
        await this.orderButton().click();
    }
}
 