import { Page, expect } from '@playwright/test';

export class NykaaPage {
    page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    searchBox = 'input[placeholder*="Search"]';

    async navigate(url: string) {
    await this.page.goto(url, {
        waitUntil: 'domcontentloaded',
        timeout: 60000
    });
}
    async verifyHomePage() {
        await expect(this.page).toHaveTitle(/Nykaa/i);
        await expect(this.page.locator('body')).toBeVisible();
    }

    async searchProduct(product: string) {
        await this.page.locator(this.searchBox).fill(product);

        if (product !== "") {
            await this.page.keyboard.press('Enter');
        }
    }

    async verifySearchResult(product: string) {
        await expect(this.page.locator('body'))
            .toContainText(new RegExp(product, 'i'));
    }

    async verifySearchBoxVisible() {
        await expect(this.page.locator(this.searchBox)).toBeVisible();
    }

    async verifyEmptySearch() {
        const value = await this.page.locator(this.searchBox).inputValue();
        expect(value).toBe("");
    }
}