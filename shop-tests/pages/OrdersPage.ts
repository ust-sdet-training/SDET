import {Page,Locator} from '@playwright/test';

export class OrdersPage{

    constructor(private readonly page:Page){}

    ordersInfo = () : Locator => this.page.locator('.table-note');

    async isOrderPresent(productName: string):Promise<boolean>{
        const orderedProducts = await this.ordersInfo().allTextContents();
        return orderedProducts.includes(productName);
    }

}