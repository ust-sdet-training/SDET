import { SearchPage } from "./SearchPage";
import { ProductPage } from "./ProductPage";
import { CartPage } from "./CartPage";
import { CheckoutPage } from "./CheckoutPage";

export class OrderFlow{
  constructor(private readonly searchPage: SearchPage,private readonly productPage: ProductPage,private readonly cartPage: CartPage,private readonly checkoutPage: CheckoutPage) {}
  async buyProduct(productName: string): Promise<void> {
    await this.searchPage.search(productName);
    await this.productPage.openProduct();
    await this.productPage.addToCart();
    await this.cartPage.open();
    await this.cartPage.proceedToCheckout();
    await this.checkoutPage.placeOrder();
  }
}