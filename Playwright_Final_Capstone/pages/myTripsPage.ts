import { Page } from '@playwright/test';

export class MyTripsPage {
  constructor(private readonly page: Page) {}

  private readonly bookingReferences = () => this.page.getByText(/TS-[A-Za-z0-9-]+/);
  private readonly trip = (pnr: string) => this.page.locator('article, .trip-card, .booking-card, li').filter({ hasText: pnr }).first();
  private readonly status = (pnr: string) => this.trip(pnr).getByText(/CONFIRMED|HELD|PAYMENT_PENDING/i).first();

  bookingReference(pnr: string) {
    return this.bookingReferences().filter({ hasText: pnr });
  }

  bookingStatus(pnr: string) {
    return this.status(pnr);
  }
}
