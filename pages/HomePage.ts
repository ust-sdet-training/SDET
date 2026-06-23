import { expect, Page } from "@playwright/test";

export class HomePage {

    constructor(private readonly page: Page) {}

    private searchBox = () => this.page.getByRole("textbox", {name: "Search"});

    async goto() {
        await this.page.goto("/home");
    }

    async verifyLogoVisible() {
        await expect(this.page.getByRole("link", {name: "logo",exact: true})).toBeVisible();
    }

    async search(product: string) {
        await this.searchBox().fill(product);
        await this.searchBox().click();
        await this.searchBox().press("Enter");
        await expect(this.page.getByRole("heading", {name: product})).toBeVisible();
    }

    async verifyNoResults() {
        await expect(
            this.page.getByText("No Results Found")
        ).toBeVisible();
    }
}