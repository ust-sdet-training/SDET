import { expect, Page } from '@playwright/test';

export class ProductPage {

    constructor(private page: Page) {}

    async verifyProductPage() {

        await this.page.waitForLoadState();

        await expect(this.page).toHaveURL(/product/i);

        await expect(
            this.page.locator("h1")
        ).toBeVisible();

    }

}