import {Locator, Page} from '@playwright/test';
 
export class Cart{
    constructor(private readonly page: Page) {}
 
    async addToCart(s: String){
        await this.page.getByRole('link', {name: `View ${s}`}).click();
        const promise = this.page.waitForResponse(
            r => r.url().includes('/api/cart') && r.status() === 200
        );
        await this.page.getByRole('button', {name: 'Add to cart'}).click();
        await promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: 'Cart'});
    badge = ():Locator => this.page.getByTestId('cart-count');
}
 