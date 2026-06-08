import { expect, Page } from "@playwright/test";
export class orderspage{
    constructor(private readonly page: Page){}
    async goto(){
        await this.page.goto('/orders');
    }

}