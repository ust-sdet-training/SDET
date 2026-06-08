import { test as base } from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";
import { AddtoCart } from "../pages/AddtoCart";
import { LoginPage } from "../pages/LoginPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderPage } from "../pages/OrderPage";

type ShopFixtures = {
  searchPage: SearchPage;
  addToCart: AddtoCart;
  loginPage: LoginPage;
  checkoutPage: CheckoutPage;
//   checkoutPage: CheckoutPage;
  orderPage: OrderPage;
};

export const test = base.extend<ShopFixtures>({
  loginPage: async ({ page }, use) => {
      await use(new LoginPage(page));
    },
  searchPage: async ({ page }, use) => {
    await use(new SearchPage(page));
  },
  addToCart: async ({ page }, use) => {
    await use(new AddtoCart(page));
  },
  checkoutPage: async({page}, use) =>{
    await use(new CheckoutPage(page));
  },
  orderPage: async ({ page }, use) => {   // ← add this block
    await use(new OrderPage(page));
  }
});

export { expect } from "@playwright/test";