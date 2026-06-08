import {Page,expect} from '@playwright/test'

export class CheckoutPage {
    constructor(private readonly page:Page) {}

    async goto(){
        await this.page.goto('/checkout');
    }

    async checkout(){
        await expect(this.page).toHaveURL(/\/checkout/);
         const res = this.page.waitForResponse(
            (r)=> r.url().includes('api/orders') && (r.status() === 200 || r.status() === 201)
        )
        await this.page.getByRole('button',{name: 'Place order'}).click();
        await res;
    }
}