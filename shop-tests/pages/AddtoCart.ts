import { Page } from "@playwright/test";
import { SearchPage } from "./SearchPage";

export class AddtoCart {
  constructor(private readonly page: Page) {}
  cartCount() {
    return this.page.getByTestId("cart-count");
  }
  async addCart(
    searchKey: string,
    productName: string
  ): Promise<void> {

    const searchPage = new SearchPage(this.page);
    await searchPage.search(searchKey);
    await searchPage.openProduct(productName);
    await this.page.getByRole("button", {name: "Add to Cart"}).click();
  }
}