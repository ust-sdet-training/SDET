import type { Page } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}

  private searchBox = () => this.page.getByRole("searchbox");
  results = () => this.page.locator(".product-grid");
  async search(q: string) {
    const response = this.page.waitForResponse(
      (response) => response.url().includes("/api/products") && response.status() === 200
    );
    await this.searchBox().fill(q);
    await this.searchBox().press('Enter');
    await response;
  }
}