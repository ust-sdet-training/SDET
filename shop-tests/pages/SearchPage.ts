import { Page } from "@playwright/test";
 
export class SearchPage {
 
  constructor(private readonly page: Page) {}
  
  async open(){
        await this.page.goto('/catalog');
    }
 
  async search(item: string): Promise<void> {
    const response = this.page.waitForResponse(resp =>resp.url().includes("/products") &&resp.status() === 200);

    await this.page.getByLabel("Search products").fill(item);
    await this.page.getByRole("button", { name: "Search" }).click();
    await response;
  }
  results() {
    return this.page.getByTestId("product-card");
  }
 
//   async openProduct(item: string): Promise<void> {
//     await this.page.getByRole("link", {name: `View ${item}`}).click();
//   }

  async firstItem(){
    return this.page.getByTestId('product-card').first().getByRole('link').click();
  }
}