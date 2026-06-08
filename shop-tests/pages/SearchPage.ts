import { Page } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}
  async search(searchKey: string): Promise<void> {

    const responsePromise =
      this.page.waitForResponse(r => r.url().includes("/products") && r.status() === 200);
    await this.page.getByLabel("Search products").fill(searchKey);
    await this.page.getByRole("button", { name: "Search" }).click();
    await responsePromise;
  }
  results() {
    return this.page.getByTestId("product-card");
  }
  noProductsMessage = () => this.page.getByText('No products found');
  async openProduct(productName: string): Promise<void> {
    await this.page.getByRole("link", {name: `View ${productName}`}).click();
  }
}