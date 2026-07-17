import { expect } from '@playwright/test';
import { BasePage } from './BasePage';

export class PaymentPage extends BasePage {
  async pay(payment: { cardName: string; cardNumber: string; expiry: string; cvv: string }) {
    await expect(this.page.getByRole('heading', { name: /secure checkout/i })).toBeVisible();
    await this.page.locator('#cardName').fill(payment.cardName);
    await this.page.locator('#cardNumber').fill(payment.cardNumber);
    await this.page.locator('#cardExpiry').fill(payment.expiry);
    await this.page.locator('#cardCvv').fill(payment.cvv);
    await this.page.getByRole('button', { name: /pay/i }).click();
    this.logger?.info('Payment completed');
  }
}
