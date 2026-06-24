import { Page, Locator, expect } from '@playwright/test';
export class HomePage {
    readonly page: Page;
    readonly searchBox: Locator;
    constructor(page: Page) {
        this.page = page;
        this.searchBox = page.getByRole('textbox', { name: 'Search' });
    }

    async openHomePage() {
        await this.page.goto('https://www.nykaa.com/', { waitUntil: 'domcontentloaded' });
    }

    async searchProduct(product: string) {
        await this.searchBox.fill(product);
        await this.searchBox.press('Enter');
    }

    async verifySearchResult(text: string) {
        await expect(this.page.locator('body')).toContainText(new RegExp(text, 'i'));
    }
    async verifyNoResult() {
        await expect(this.page.getByText("Thanks for visiting our website!Unfortunately, we couldn’t find any matches for InvalidProduct12345. Please try searching with another term."));
    }
}