import { Page } from "@playwright/test";
import { HomePage } from "../pages/NykaPage";

export class NykaaFlow {

    private readonly homePage: HomePage;

    constructor(private readonly page: Page) {
        this.homePage = new HomePage(page);
    }

    async searchValidProduct(productName: string) {
        await this.homePage.goto();
        await this.homePage.search(productName);
        await this.homePage.validateValidSearch(productName);
    }

    async searchInvalidProduct(productName: string) {
        await this.homePage.goto();
        await this.homePage.search(productName);
        await this.homePage.validateInvalidSearch(productName);
    }
}