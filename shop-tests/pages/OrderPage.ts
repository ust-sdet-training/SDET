import { test, Page, expect } from '@playwright/test';

export class OrderPage {
    constructor(private readonly page: Page) {}

    async goto() {
        const res = this.page.waitForResponse(
            (response) => response.url().includes("/api/orders") && response.status() === 200
        )
        this.page.goto("/orders");
        await res;
    }

    async validateOrder() {
        await expect(this.page).toHaveURL(/\/orders/);
        await expect(this.page.getByTestId("orders-count")).not.toHaveText("3");
    }
}