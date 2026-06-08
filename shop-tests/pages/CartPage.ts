import {expect, type Page, type Locator} from "@playwright/test"

export class CartPage{

    constructor (private readonly page: Page){};

    async cart(){
        
        const promise = this.page.waitForResponse(
            response => response.url().includes('/api/cart') && response.status()===200
        );

        await this.page.getByRole('button', {name: "Proceed to check"}).click();

        await promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: "Checkout"})

}