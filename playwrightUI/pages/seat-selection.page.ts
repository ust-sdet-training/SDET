import { expect, type Locator, type Page } from '@playwright/test';

export class SeatSelectionPage {
  constructor(private readonly page: Page) {}

  async selectAvailableSeat(index = 0): Promise<void> {
    const candidates = this.page.getByRole('button', { name: /seat/i });
    const enabledSeats: Locator[] = [];

    const count = await candidates.count();
    for (let i = 0; i < count; i += 1) {
      const seat = candidates.nth(i);
      const label = await seat.getAttribute('aria-label');
      const isEnabled = await seat.isEnabled();
      const isUnavailable = /booked|unavailable|reserved/i.test(label ?? '');

      if (isEnabled && !isUnavailable) {
        enabledSeats.push(seat);
      }
    }

    const seat = enabledSeats[index];
    if (!seat) {
      throw new Error('No enabled seat available for selection.');
    }

    await expect(seat).toBeVisible({ timeout: 15_000 });
    await seat.click();
    await expect(seat).toHaveAttribute('aria-pressed', 'true');
  }

  async continueToPassengerDetails(): Promise<void> {
    await this.page.getByRole('button', { name: 'Continue to passenger details' }).click();
    await expect(this.page).toHaveURL(/\/book\/passenger/);
  }
}
