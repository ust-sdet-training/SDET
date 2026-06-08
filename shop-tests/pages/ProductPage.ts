import { Page } from "@playwright/test";

export class ProductPage {
  constructor(private readonly page: Page) {}

  async selectSize(size: string) {
    await this.page.getByLabel("Size").selectOption({ label: size });
  }

  async setQuantity(qty: string) {
    await this.page.getByLabel("Quantity").fill(qty);
  }

  async chooseColor(color: string) {
    await this.page.getByRole("radio", { name: color }).click();
  }

  async chooseDelivery(method: string) {
    await this.page.getByRole("radio", { name: method }).click();
  }

  async addToCart() {
    await this.page.getByRole("button", { name: "Add to cart" }).click();
  }
}