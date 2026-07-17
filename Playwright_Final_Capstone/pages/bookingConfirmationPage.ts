import { Page } from '@playwright/test';

export class BookingConfirmationPage {
  constructor(private readonly page: Page) {}

  private readonly bookingReferenceLabelText = () => this.page.getByText(/booking reference/i);
  private readonly bookingReferenceValue = () =>
    this.page.getByText(/TS-[A-Za-z0-9-]+/).first();
  private readonly confirmedStatus = () => this.page.getByText('CONFIRMED', { exact: true }).first();
  private readonly myTripsButton = () => this.page.getByRole('link', { name: /my trips/i }).first();

  bookingReferenceLabel() {
    return this.bookingReferenceLabelText();
  }

  bookingReference() {
    return this.bookingReferenceValue();
  }

  confirmationStatus() {
    return this.confirmedStatus();
  }

  async getBookingReference(): Promise<string> {
    return (await this.bookingReference().innerText()).trim();
  }

  async viewMyTrips(): Promise<void> {
    await this.myTripsButton().click();
  }
}
