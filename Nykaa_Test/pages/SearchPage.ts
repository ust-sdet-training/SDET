import { Page, expect } from "@playwright/test";

export class SearchPage {

    constructor(private page: Page) {}

    async verifySearchResults() {

        await this.page.waitForURL(/search/i);
        await expect(this.page.locator("[href*='/p/']").first()).toBeVisible();

    }

    async openFirstProduct() {

        const firstProduct = this.page.locator("[href*='/p/']").first();
        const [newPage] = await Promise.all([
            this.page.context().waitForEvent("page"),
            firstProduct.click()
        ]);
        await newPage.waitForLoadState();
        return newPage;
    }
}