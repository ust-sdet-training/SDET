import { expect, type Page } from "@playwright/test";

export class ProductPage {
    constructor(private readonly page: Page) {}
    
    async addToCart() {
        await expect(this.page).toHaveURL(/\/product/);
        const res = this.page.waitForResponse(
            (response) => response.url().includes("/api/cart") && response.status() === 200
        )
        await this.page.getByRole("button", {name: "Add to cart"}).click();
        await res;
    }
}