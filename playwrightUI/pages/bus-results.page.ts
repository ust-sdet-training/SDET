import { expect, type Locator, type Page } from '@playwright/test';

export class BusResultsPage {
  constructor(private readonly page: Page) {}

  async expectRoute(from: string, to: string): Promise<void> {
    await expect(this.page.getByRole('heading', { name: `Buses from ${from} to ${to}` })).toBeVisible({ timeout: 60_000 });
  }

  async filterByAcSemiSleeper(): Promise<Locator> {
    await this.page.getByLabel('Semi-Sleeper').check({ timeout: 60_000 });
    const bus = this.page.locator('article[data-kind="ac-semi"]').first();
    await expect(bus).toBeVisible({ timeout: 60_000 });
    return bus;
  }

  async selectSeats(bus: Locator): Promise<void> {
    await bus.getByRole('button', { name: 'Select Seats' }).click();
    await expect(this.page).toHaveURL(/\/seatmap/);
  }
}
