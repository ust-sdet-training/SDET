import type { expect,Page } from "@playwright/test";

export class OrderPage{
    constructor(private readonly page: Page){}

    async open(){
        await this.page.goto('/orders');
    }

    orderIdinOrders(){
        return this.page.locator("tbody>tr");
    }

    
}


