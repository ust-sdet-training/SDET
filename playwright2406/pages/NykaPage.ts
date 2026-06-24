import { Page, expect } from "@playwright/test";

export class HomePage {

    constructor(private readonly page: Page) {}

    private searchBox = () => this.page.getByPlaceholder('Search on Nykaa');
    heading = () => this.page.locator('h1');
    private productList = () => this.page.locator('#product-list-wrap');
    private noResultMessage = () => this.page.locator('.subtitle');
    async goto() {
        await this.page.goto('https://www.nykaa.com/', {
            waitUntil: 'domcontentloaded'
        });

        await expect(this.page).toHaveTitle(/Nykaa/i);
    }

    async search(productName: string) {
        await this.searchBox().fill(productName);
        await this.page.keyboard.press('Enter');
    }

    async validateValidSearch(productName: string) {
        await expect(this.heading()).toBeVisible();
        await expect(this.heading()).toContainText(productName);
        await expect(this.productList()).toBeVisible();
    }

    async validateInvalidSearch(productName: string) {
        await expect(this.noResultMessage()).toBeVisible();

        await expect(this.noResultMessage())
            .toContainText(
                `Unfortunately, we couldn’t find any matches for ${productName}`
            );
    }
}