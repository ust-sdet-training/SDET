import { expect, type Page } from '@playwright/test';

export class MyTripsPage {
  constructor(private readonly page: Page) {}

  async goto(): Promise<void> {
    await this.page.getByRole('link', { name: 'My Trips' }).click();
    await expect(this.page.getByRole('heading', { name: 'My Trips' })).toBeVisible();
  }

  async expectPnr(pnr: string): Promise<void> {
    await expect(this.page.getByText(pnr, { exact: true })).toBeVisible();
  }
}
