import { Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { ProductsPage } from "../pages/ProductsPage";

export class TestFlow {
    readonly homePage: HomePage
    readonly productsPage: ProductsPage

    constructor(readonly page: Page) {
        this.homePage = new HomePage(page);
        this.productsPage = new ProductsPage(page);
    }

    async positiveTest(prod: string) {
        await this.homePage.searchExisting(prod)
        await this.productsPage.existingProducts(prod)
    }

    async negativeTest(prod: string) {
        await this.homePage.searchExisting(prod)
        await this.productsPage.nonExistingProducts(prod)
    }
}