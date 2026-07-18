import { Page } from '@playwright/test';

export class MyTripsPage {
  constructor(private readonly page: Page) {}

  private readonly status = (pnr: string) =>
    this.bookingReference(pnr).locator('xpath=..').getByText(/CONFIRMED|HELD|PAYMENT_PENDING/i).first();

  bookingReference(pnr: string) {
    return this.page.getByText(pnr, { exact: true });
  }

  bookingStatus(pnr: string) {
    return this.status(pnr);
  }
}
