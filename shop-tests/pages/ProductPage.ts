import { expect, Page } from "@playwright/test";

export class ProductPage{
    constructor(private readonly page: Page) {}  
    async goto(productName: string) {
        await this.page.goto(`/product/${productName}`);
    }

    //resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async addToCart(): Promise<void> {
        const res = this.page.waitForResponse(
            r => r.url().includes('cart/items') &&
                    r.status() === 200);
        
        await this.page.getByRole('button', { name: 'Add to cart' }).click();
        await res;
    }
}