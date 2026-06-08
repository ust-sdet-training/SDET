import { expect, type Page, Locator } from "@playwright/test";
import { testUsers } from "../fixtures/test.ts";
export class ProductPage {
  constructor(private readonly page: Page) {}

    async addProductToCart(productName: String){
        await this.page.getByRole('link', {name: `View ${productName}`}).click();
        const promise = this.page.waitForResponse(
            r => r.url().includes('/api/cart') && r.status() === 200
        );
        await this.page.getByRole('button', {name: 'Add to cart'}).click();
        await promise;
    }
    result = ():Locator => this.page.getByRole('heading', {name: 'Cart'});
    badge = ():Locator => this.page.getByTestId('cart-count');
}
