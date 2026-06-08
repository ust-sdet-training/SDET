import { test as base } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderPage } from "../pages/OrderPage";
 
type ShopFixtures = {
    loginPage: LoginPage;
    homePage: HomePage;
    searchPage: SearchPage;
    productPage:ProductPage;
    cartPage: CartPage;
    checkoutPage: CheckoutPage;
    orderPage: OrderPage;
};
 
export const test = base.extend<ShopFixtures>({
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },
 
  homePage: async ({ page }, use) => {
    await use(new HomePage(page));
  },
  
  searchPage: async ({ page }, use) => {
    await use(new SearchPage(page));
  },

  productPage: async ({ page }, use) => {
    await use(new ProductPage(page));
  },

  cartPage: async ({ page }, use) => {
    await use(new CartPage(page));
  },

  checkoutPage: async ({ page }, use) => {
    await use(new CheckoutPage(page));
  },

  orderPage: async ({ page }, use) => {
    await use(new OrderPage(page));
  }
});
 
export { expect } from "@playwright/test";