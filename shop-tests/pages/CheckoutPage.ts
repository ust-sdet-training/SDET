import { Page } from "@playwright/test";

export class CheckoutPage {
  constructor(private readonly page: Page) {}
  async proceedToCheckout() {
    await this.page.getByRole("link", { name: /cart/i }).click();
    await this.page.getByRole("button", { name: /checkout/i }).click();
  }
  async placeOrder() {
    await this.page.getByRole("button", { name: /place order/i }).click();
  }
}