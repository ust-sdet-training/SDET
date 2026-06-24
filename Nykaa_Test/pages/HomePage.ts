import { Page, expect } from "@playwright/test";

export class HomePage {

    constructor(private page: Page) {}

    async openHomePage() {
        await this.page.goto("https://www.nykaa.com/");
        await expect(this.page).toHaveTitle(/Nykaa/i);
    }

    async search(product: string) {

        const search = this.page.locator('input[name="search-suggestions-nykaa"]');

        await expect(search).toBeVisible();
        await search.fill(product);
        await search.press("Enter");
    }
}