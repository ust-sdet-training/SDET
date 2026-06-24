import { Page, expect } from '@playwright/test';

export class HomePage {

    constructor(private page: Page) {}

    private get searchBox() {
        return this.page.getByRole('textbox', { name: 'Search on Nykaa' });
    }

    async navigate() {
        await this.page.goto('https://www.nykaa.com/', { waitUntil: 'domcontentloaded' });
    }

    async verifyHomePageLoaded() {
        await expect(this.searchBox).toBeVisible();
    }

    async searchProduct(product: string) {
        await expect(this.searchBox).toBeVisible();
        await this.searchBox.fill(product);
        await this.page.keyboard.press('Enter');
    }
}