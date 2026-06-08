import { Page } from '@playwright/test';

export class CheckoutPage {

    constructor(private page: Page){}

    async checkout(){
        await this.page.getByRole('button',{name:'Checkout'}).click();
    }

    async placeOrder(){
        await this.page.getByRole('button',{name:'Place order'}).click();
    }
}