import { expect, type Page } from "@playwright/test";

export class SearchPage {
    constructor(private readonly page: Page) {}
    private searchBox = () => this.page.getByRole("searchbox");
    private searchButton = () => this.page.getByRole("button", {name: "Search"});
    results = () => this.page.getByTestId("product-card");

    async goto() {
        await this.page.goto("/catalog");
    }

    async search(query: string) {
        const searchResponse = this.page.waitForResponse(
            (response) => response.url().includes("/api/products") && response.status() === 200
        )
        await this.searchBox().fill(query);
        await this.searchButton().click();
        await searchResponse;
    }

    async isSearchCountEmpty() {
        await expect.soft(this.page.getByTestId("catalog-result-count")).not.toHaveText(`Showing 0 products`);
    }

    async selectFirstProduct() {
        await this.results().getByRole("link", {name: /view/i}).click();
    }

    async viewProductByName(productName: string) {
        await this.results().getByRole("link", {name: `View ${productName}`}).click();
    }
}
