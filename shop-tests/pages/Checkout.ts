import {expect, type Page, type Locator} from "@playwright/test"

export class Checkout{

    constructor (private readonly page: Page){};

    async add(){
        
        const promise = this.page.waitForResponse(
            response => response.url().includes('/api/orders') && response.status()===200
        );

        await this.page.getByRole('button', {name: "Place order"}).click();

        await promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: "Thank you for your order"});

}