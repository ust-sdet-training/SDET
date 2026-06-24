import { expect, Page } from "@playwright/test";

export class HomePage{
    constructor(private readonly page: Page){}

    private searchBox = () => this.page.getByRole("textbox", {name: "Search on Nykaa"});

    async goto(){
        await this.page.goto("/", {waitUntil: 'domcontentloaded'});
    }

    async search(query:string){
        await this.searchBox().click();
        await this.searchBox().fill(query);
        await this.searchBox().press('Enter');
    }
}
