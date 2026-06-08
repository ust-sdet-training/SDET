import { expect, Page } from "@playwright/test";

export class CartPage {
  constructor(private readonly page: Page) {}

  async expectCartCount(count: string) {
    await expect(this.page.getByTestId("cart-count")).toHaveText(count);
  }
}