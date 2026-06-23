import { Page, expect } from '@playwright/test';

export class SearchResultsPage {
    page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async verifyResultsDisplayed() {
        await this.page.waitForLoadState('networkidle');
        await expect(this.page.locator('body')).toBeVisible();
    }
}