import {Page, Locator} from '@playwright/test';

export class Checkout_order{
    constructor(private readonly page: Page){}

    async order(){
        const Promise = this.page.waitForResponse(
            r => r.url().includes('/api/orders') && r.status() === 201
        );
        await this.page.getByRole('button', {name: 'Place order'}).click();
        await Promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: 'Thank you for your order'});
}