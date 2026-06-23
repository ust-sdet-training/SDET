import { expect, Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage";

export class HomeFlow {

    private readonly homePage: HomePage;

    constructor(private readonly page: Page) {
        this.homePage = new HomePage(page);
    }

    async verifyHomePage() {
        await this.homePage.goto();
        await this.homePage.verifyLogoVisible();

    }
    
    async searchValidProduct() {
        const product = "Watches";
        await this.homePage.goto();
        await this.homePage.search(product);
    }

    async searchInvalidProduct() {
        const product = "xyz";
        await this.homePage.goto();
        await this.homePage.search(product);
        await this.homePage.verifyNoResults();
    }
}