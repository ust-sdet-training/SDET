import { expect, type Page } from "@playwright/test";


export class HomePage{
    constructor(private readonly page: Page) {}
    

    async goto(){
        this.page.goto("/home");
    }

    async productsPage(){
        const btn = await this.page.getByRole("navigation", {name: "Primary navigation"}).getByRole("link", {name: "Products"}).click();
    }
}