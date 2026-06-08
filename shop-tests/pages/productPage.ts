import { expect, Locator, Page } from "@playwright/test";

export class ProductPage{
    constructor(private readonly page:Page){}
    
    async addToCart(){
        
        await expect(this.page).toHaveURL(/\/product/);
        const res = this.page.waitForResponse(
            (r)=> r.url().includes('/api/cart') && (r.status() === 200 || r.status() === 204)
        )
        await this.page.getByRole('button',{name: 'Add to cart'}).click();
        await res;

    }


}