import { Page } from '@playwright/test';

export class HomePage{
    constructor(private page:Page){}

    async open(){
        await this.page.goto("https://www.shoppersstop.com/", { waitUntil: "domcontentloaded" });
    }
}
