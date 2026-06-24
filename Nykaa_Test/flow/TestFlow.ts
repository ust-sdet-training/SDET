import { Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";

export class TestFlow {

    home: HomePage;
    search: SearchPage;

    constructor(private page: Page) {
        this.home = new HomePage(page);
        this.search = new SearchPage(page);

    }

    async execute() {
        await this.home.openHomePage();
        await this.home.search("lipstick");
        await this.search.verifySearchResults();
        const productTab = await this.search.openFirstProduct();
        const product = new ProductPage(productTab);
        await product.verifyProductPage();
        await product.addToBag();
        await product.verifyBagIcon();
    }

}