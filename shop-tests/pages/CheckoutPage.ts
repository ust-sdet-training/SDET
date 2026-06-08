import { test, Page, expect } from '@playwright/test';

export class CheckoutPage {
    constructor(private readonly page: Page) {}

    async goto() {
        this.page.goto("/checkout");
    }

    async placeOrder() {
        await expect(this.page).toHaveURL(/\/checkout/);
        const res = this.page.waitForResponse(
            (response) => response.url().includes("/api/orders") && response.status() === 201 || response.status() === 200
        )
        await this.page.getByRole("button", {name: "Place order"}).click();
        await res;
    }
}