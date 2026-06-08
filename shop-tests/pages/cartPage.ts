import {Page,expect} from '@playwright/test'

export class CartPage {
    constructor(private readonly page:Page) {}

    async goto(){
        await this.page.goto('/cart');
    }

    async checkout(){
        await expect(this.page).toHaveURL(/\/cart/);
         const res = this.page.waitForResponse(
            (r)=> r.url().includes('/api/cart') && (r.status() === 200 || r.status() === 204)
        )
        await this.page.getByRole('button',{name: 'Proceed to checkout'}).click();
        await res;
    }

}