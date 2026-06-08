import type { Page } from '@playwright/test';

export class AddtocartPage {
  constructor(private readonly page: Page) {}

  async addItem() {
    await this.page.getByRole('link', { name: /view/i }).first().click();
    await this.page.getByRole('button', { name: /cart/i }).click();
  }
}