import {type Page, expect} from "@playwright/test"

export class HomePage{
    constructor(private readonly page: Page) {}
    logo = () => this.page.getByLabel("Nykaa Logo");
    private searchbar = () => this.page.getByRole("textbox", {name: "Search on Nykaa"});

    productList = () => this.page.locator(".product-listing");
    async goto(){
        await this.page.goto("/", {waitUntil: 'domcontentloaded'});
    }

    async search(query: string){
        await this.searchbar().fill(query);
        await this.searchbar().press("Enter");
    }

    async firstProduct(){
        await this.productList().first().click();
    }
}