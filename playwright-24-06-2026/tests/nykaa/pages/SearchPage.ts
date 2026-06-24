import type { Page } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}

  private searchBox = () => this.page.getByRole('textbox', { name: 'Search on Nykaa' });
  async search(q: string) {
    await this.searchBox().fill(q);
    await this.searchBox().press('Enter');
  }
}