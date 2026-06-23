import { expect, Page } from '@playwright/test';

export class SearchPage {

    constructor(private page: Page) {}

    async verifySearchResults(product: string) {

        await expect(this.page).toHaveURL(/search/i);
        await expect(this.page.getByRole('heading', {name: new RegExp(product, 'i')})).toBeVisible();
        const products = this.page.locator("[data-testid='product-card']");
        await expect(products.first()).toBeVisible();
    }

    async applyWomenFilter() {
        await this.page.getByRole('button', { name: /Department/i }).click();
        await this.page.locator("#Women").click();
        await this.page.waitForLoadState('networkidle');
        await expect(this.page.locator("body")).toContainText("Women");
    }

    async openFirstProduct() {
        const popupPromise = this.page.waitForEvent('popup');
        await this.page.locator("[data-testid='product-card']").first().click();
        return await popupPromise;
    }

}