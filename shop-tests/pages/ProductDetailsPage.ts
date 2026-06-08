import { expect, Locator, Page } from '@playwright/test';

export class ProductDetailsPage {
    readonly page: Page;
    readonly addToCartButton: Locator;
    readonly quantity: Locator;

    constructor(page: Page) {
        this.page = page;

        this.addToCartButton =
            page.getByRole('button', {
                name: 'Add to cart'
            });

        this.quantity = page.getByLabel('Quantity')
    }

    async addToCart(): Promise<void> {
        await this.addToCartButton.click();
    }

    async checkQuantity() {
        const value = Number(await this.quantity.inputValue())
        expect(value).toBeGreaterThan(0)
        expect(value).toBeLessThan(6)
    }
}