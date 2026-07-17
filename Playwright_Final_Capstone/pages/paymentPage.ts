import { Page } from '@playwright/test';
import type { CardDetails } from '../types/booking';

export class PaymentPage {
  constructor(private readonly page: Page) {}

  private readonly nameBox = () => this.page.getByLabel(/name on card/i);
  private readonly cardNumberBox = () => this.page.getByLabel(/card number/i);
  private readonly expiryBox = () => this.page.getByLabel(/expiry/i);
  private readonly cvvBox = () => this.page.getByLabel(/cvv/i);
  private readonly payButton = () => this.page.getByRole('button', { name: /pay/i });

  async pay(card: CardDetails): Promise<void> {
    await this.nameBox().fill(card.name);
    await this.cardNumberBox().fill(card.number);
    await this.expiryBox().fill(card.expiry);
    await this.cvvBox().fill(card.cvv);
    await this.payButton().click();
  }
}
