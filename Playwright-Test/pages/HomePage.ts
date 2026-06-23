import { expect, type Page } from "@playwright/test";

export class HomePage{
    constructor(private readonly page: Page){}

        private searchBox = () => this.page.getByRole("textbox", {name: "Search"});

    async goto(){
        await this.page.goto("/");
        await expect(this.page).toHaveTitle("Shoppers Stop End of Season Sale | Up to 50% Off Across Categories");
    }

    async search(query: string ){
        await this.searchBox().fill(query);
        await this.searchBox().click();
        await this.page.getByText("Suggestions").waitFor({state: "visible"});
        await this.searchBox().press("Enter");
        await expect(this.page.getByRole("heading", {name: query})).toBeVisible();
    }

}