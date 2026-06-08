import { Page, expect } from "@playwright/test";

export class ProductPage {
  constructor(private readonly page: Page) {}

  async openProduct(): Promise<void> {
    const viewProductLink = this.page.getByRole("link", {
      name: /view running shoes/i
    });

    await expect(viewProductLink).toBeVisible();
    await viewProductLink.click();
  }

  async addToCart(): Promise<void> {
    const addToCartResponse = this.page.waitForResponse(
      response =>
        response.url().includes("/cart") &&
        response.status() === 201
    );

    await this.page
      .getByRole("button", { name: /add to cart/i })
      .click();

    await addToCartResponse;
  }
}