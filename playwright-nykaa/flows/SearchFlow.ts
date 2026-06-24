import { Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage"

export class SearchFlow{
    readonly homePage: HomePage;
    constructor(private readonly page: Page) {
        this.homePage = new HomePage(this.page);
    }

    search = async (query: string) => {
        await this.homePage.goto();
        await this.homePage.searchProduct(query);
    }
}