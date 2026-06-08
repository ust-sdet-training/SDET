import type { Page } from '@playwright/test';

export class CheckoutPage {
  constructor(private readonly page: Page) {}

  async goToCheckout() {
    await this.page.getByRole('link', { name: /cart/i }).click();
    await this.page.getByRole('link', { name: /checkout/i }).click();
  }
}