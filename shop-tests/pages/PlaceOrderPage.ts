import { CartPage } from './CartPage';
import { expect,type Page } from '@playwright/test';
import { SearchPage } from './SearchPage';

export class PlaceOrderPage{
    constructor(private readonly page: Page) {}

    async placeOrder(){
        const cartProduct=new CartPage(this.page);
        await cartProduct.cart();
        await this.page.getByRole('button',{name:'Place order'}).click();
        await expect(this.page.getByRole('heading',{name:'Thank you for your order'})).toBeVisible();
        await this.page.getByRole('button',{name:'View orders'}).click();
        await expect(this.page.getByRole('heading',{name:'Orders'})).toBeVisible();
            
        
    }
}