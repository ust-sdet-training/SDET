import { Page } from '@playwright/test';

export class MyTripsPage {
  constructor(private readonly page: Page) {}

  private readonly bookingReferences = () => this.page.getByText(/TS-[A-Za-z0-9-]+/);
  private readonly status = (pnr: string) =>
    this.bookingReference(pnr).locator('xpath=..').getByText(/CONFIRMED|HELD|PAYMENT_PENDING/i).first();

  bookingReference(pnr: string) {
    return this.bookingReferences().filter({ hasText: pnr });
  }

  bookingStatus(pnr: string) {
    return this.status(pnr);
  }
}
