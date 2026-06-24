import { Page, expect } from '@playwright/test';

export class SearchResultsPage {

    constructor(private page: Page) {}

    async verifySearchResults() {
        await expect(this.page).toHaveURL(/search/i);
        await expect(this.page.locator('.page-title-search')).toContainText("watch");
    }

    async verifyNoResultsMessage() {
        await expect(this.page.getByText('Unfortunately')).toBeVisible();
    }
}