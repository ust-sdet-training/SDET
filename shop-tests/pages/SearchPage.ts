import { Page, Locator } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}

  searchBox = (): Locator =>
    this.page.getByRole("searchbox");

  searchButton = (): Locator =>
    this.page.getByRole("button", { name: "Search" });

  result = (product: string): Locator =>
    this.page.getByRole("link", {
      name: `View ${product}`,
    });

  async search(query: string) {
    await this.searchBox().fill(query);
    await this.searchButton().click();
  }
}