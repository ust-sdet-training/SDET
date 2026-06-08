import { Page, expect } from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { LoginPage } from "../pages/LoginPage";

export class ShopFlow {
  constructor(private page: Page) {}

  async searchAndAddToCart(product: string) {
    const search = new SearchPage(this.page);
    const productPage = new ProductPage(this.page);
    const cart = new CartPage(this.page);
    const login = new LoginPage(this.page);

    await this.page.goto("/catalog");

    await search.search(product);

    await expect(search.result(product)).toBeVisible();
    await search.result(product).click();

    await productPage.selectSize("UK 7");
    await expect(this.page.getByLabel("Size")).toHaveValue("UK 7");

    await productPage.setQuantity("2");
    await expect(this.page.getByLabel("Quantity")).toHaveValue("2");
    await productPage.chooseColor("Black");
    await productPage.chooseDelivery("Home delivery");
    const added = this.page.waitForResponse(
      (resp) =>
        resp.url().includes("/cart") &&
        resp.request().method() === "POST" &&
        resp.ok(),
    );

    await productPage.addToCart();

    await added;

    await cart.expectCartCount("2");
  }
}
