import { expect, type Page } from '@playwright/test';

export class LandingPage {
  constructor(private readonly page: Page) {}

  async openBuses(): Promise<void> {
    await expect(this.page.getByRole('heading', { name: 'Book flights & buses across India' })).toBeVisible();
    await this.page.getByRole('link', { name: 'Buses', exact: true }).click();
    await expect(this.page).toHaveURL(/\/buses\/search/);
  }
}
