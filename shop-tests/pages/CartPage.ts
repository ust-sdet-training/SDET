import {type Page,expect} from '@playwright/test';
import { SearchPage } from './SearchPage';

export class CartPage{
    constructor(private readonly page: Page) {}

    async cart(){
        const searchProduct=new SearchPage(this.page);
            await searchProduct.search();
            
            this.page.goto('/cart');
            await expect.soft(this.page.getByTestId('cart-count')).toHaveCount(1);
            await this.page.getByRole('button',{name:'Proceed to checkout'}).click();
            await this.page.goto('/checkout');
    }
}