import { test, expect } from "../fixtures/shopFixture";
import { users } from "../utils/test-data";

test.describe("Complete Shop Flow", () => {
  const searchKey = "running";
  const productName = "Running Shoes";
  test("Login -> Search ->  Cart -> Checkout -> Order",
    async ({
      page,
      loginPage,
      searchPage,
      addToCart,
      checkoutPage,
      orderPage
    }) => {
      await page.goto("/login");
      await loginPage.login(
        users.customer.email,
        users.customer.password
      );
      await page.goto("/catalog");
      await searchPage.search(searchKey);
      await expect(searchPage.results()).toHaveCount(1);
      await searchPage.openProduct(productName);
      await expect(page).toHaveURL("/product/running-shoes");
      await page.getByRole("button", {name: "Add to Cart"}).click();
      await expect(addToCart.cartCount()).toHaveText("1");
      await checkoutPage.proceedToCheckout();
      await checkoutPage.placeOrder();
      await expect(orderPage.orderSuccessTitle()).toBeVisible();
      await expect(orderPage.orderConfirmation()).toContainText("confirmed");
      await expect(orderPage.viewOrdersButton()).toBeVisible();
    });

});