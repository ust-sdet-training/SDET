import { expect, type Page } from '@playwright/test';

export class SeatSelectionPage {
  constructor(private readonly page: Page) {}

  async selectAvailableSeat(index = 0): Promise<void> {
    const seat = this.page.getByRole('button', { name: /^Seat .+ available$/ }).nth(index);
    await seat.click();
    await expect(seat).toHaveAttribute('aria-pressed', 'true');
  }

  async continueToPassengerDetails(): Promise<void> {
    await this.page.getByRole('button', { name: 'Continue to passenger details' }).click();
    await expect(this.page).toHaveURL(/\/book\/passenger/);
  }
}
