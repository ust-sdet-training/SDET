import {expect,Locator,Page} from '@playwright/test';

export class CartPage{
    constructor(private readonly page:Page){}

    checkout_button =():Locator => this.page.getByRole('button',{name:"Proceed to checkout"});

    order_button =():Locator => this.page.getByRole('button',{name:"Place Order"});
    async proceed_to_checkout(){
        await this.checkout_button().click();
    }

    async place_order(){
        await this.order_button().click();
    }
}