import {Page,expect} from '@playwright/test'

export class OrderPage {
    constructor(private readonly page:Page) {}

    async goto(){
        const res = this.page.waitForResponse(
            (r)=> r.url().includes('/api/order') && (r.status() === 200 || r.status() === 204)
        )
        await this.page.goto('/orders');
        await res;
        
    }

    async validateOrder(){
        await expect(this.page).toHaveURL(/\/orders/);
        await expect(this.page.getByTestId('orders-count')).not.toHaveText('3');
    }

}