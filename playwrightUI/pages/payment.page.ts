import { expect, type Page } from '@playwright/test';
import { env } from '../support/env';

export class PaymentPage {
  constructor(private readonly page: Page) {}

  async pay(): Promise<void> {
    const card = env.paymentCard();
    await expect(this.page.getByRole('heading', { name: 'Secure checkout' })).toBeVisible();
    await this.page.locator('input[name="cardName"]').fill(card.name);
    await this.page.locator('input[name="cardNumber"]').fill(card.number);
    await this.page.locator('input[name="cardExpiry"]').fill(card.expiry);
    await this.page.locator('input[name="cardCvv"]').fill(card.cvv);
    await this.page.getByRole('button', { name: /^Pay ₹/ }).click();
  }
}
