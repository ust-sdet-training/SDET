import { Page, expect } from '@playwright/test';

export class HomePage {
    page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigate() {
        await this.page.goto('https://www.shoppersstop.com/');
    }

    async verifyHomePageLoaded() {
        await expect(this.page).toHaveTitle(/Shoppers Stop/i);
    }

    async searchProduct(product: string) {
        const searchBox = this.page.locator('input').first();
        await searchBox.fill(product);
        await this.page.keyboard.press('Enter');
        await this.page.goto('https://www.shoppersstop.com/', {
        waitUntil: 'domcontentloaded',
       timeout: 60000
});
    }
}