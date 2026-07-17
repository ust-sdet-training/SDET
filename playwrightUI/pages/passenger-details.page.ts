import { expect, type Page } from '@playwright/test';
import { env } from '../support/env';

export class PassengerDetailsPage {
  constructor(private readonly page: Page) {}

  async enterDetails(): Promise<void> {
    const passenger = env.passenger();
    await expect(this.page.getByRole('heading', { name: "Who's travelling?" })).toBeVisible();
    await this.page.locator('input[name^="firstName_"]').fill(passenger.firstName);
    await this.page.locator('input[name^="lastName_"]').fill(passenger.lastName);
    await this.page.locator('input[name^="passengerAge_"]').fill(passenger.age);
    await this.page.locator('input[name="email"]').fill(env.credentials().email);
    await this.page.locator('input[name="phone"]').fill(passenger.phone);
  }

  async submit(): Promise<'payment' | 'seat-conflict'> {
    await this.page.getByRole('button', { name: 'Continue to payment' }).click();
    const conflict = this.page.getByRole('heading', { name: 'Seat no longer available' });
    let outcome: 'payment' | 'seat-conflict' | 'pending' = 'pending';

    await expect.poll(async () => {
      if (/\/book\/payment\//.test(this.page.url())) return 'payment';
      if (await conflict.isVisible()) return 'seat-conflict';
      return 'pending';
    }, { timeout: 15_000 }).not.toBe('pending');

    outcome = /\/book\/payment\//.test(this.page.url()) ? 'payment' : 'seat-conflict';
    return outcome;
  }
}
