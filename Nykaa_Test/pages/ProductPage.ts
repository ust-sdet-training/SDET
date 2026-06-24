import { Page, expect } from "@playwright/test";

export class ProductPage {

    constructor(private page: Page) {}

    async verifyProductPage() {
        await expect(
            this.page.getByRole("button", {name: /Add to Bag/i})
        ).toBeVisible();

    }
    async addToBag() {
        await this.page.getByRole("button", {
            name: /Add to Bag/i
        }).click();

    }
    async verifyBagIcon() {

        await expect(
            this.page.locator("button").filter({
                hasText: /Bag/i
            })
        ).toBeVisible();

    }
}