import {Locator, Page} from '@playwright/test';
export class CartPage{
    constructor(private readonly page: Page) {}

    async checkout(){
        const promise = this.page.waitForResponse(
            r => r.url().includes('/api/cart')
        );
        await this.page.getByRole('button', {name: 'Proceed to checkout'}).click();
        await promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: 'Checkout'});
}