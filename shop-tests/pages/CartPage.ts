import { test, Page, expect } from '@playwright/test';

export class CartPage {
    constructor(private readonly page: Page) {}

    async goto() {
        await this.page.goto("/cart");
    }

    async proceedToCheckout() {
        await expect(this.page).toHaveURL(/\/cart/);
        const res = this.page.waitForResponse(
            (response) => response.url().includes("/api/cart") && response.status() === 200
        )
        await this.page.getByRole("button", {name: "Proceed to checkout"}).click();
        await res;
    }
}