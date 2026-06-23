import { expect, Page } from '@playwright/test';
export class dashboard {
    constructor(private readonly page: Page) {}

    async Search(itemName: string) {
        const searchInput = this.page.locator(
            'input[placeholder*="Search" i], input[aria-label*="search" i], input[type="search"], input[name*="search" i]'
        ).first();

        await expect(searchInput).toBeVisible();
        await searchInput.fill(itemName);
        await searchInput.press('Enter');
    }

    async NavigateToProductPage(itemName: string) {
        await this.page.goto(
            `https://www.shoppersstop.com/search/result?q=${encodeURIComponent(itemName)}`
        );
    }
}