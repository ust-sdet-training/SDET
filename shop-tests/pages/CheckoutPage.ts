import { Page } from "@playwright/test";
export class CheckoutPage {
  constructor(private readonly page: Page) {}
  async placeOrder(): Promise<void> {
    const ordered=this.page.waitForResponse(response =>response.url().includes('/orders') &&response.status() === 201);
    await this.page.getByRole('button', { name: /place order/i}).click();
    await ordered;
  }
}