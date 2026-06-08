import AxeBuilder from "@axe-core/playwright";
import { test, expect } from "../fixtures/shopFixture";
import {users} from "../utils/test-data"

test.describe("Shop Flow", () => {

  var searchKey = "running";
  const productName = "Running Shoes";

  test("login", async({page,loginPage})=>{
    await page.goto('/login');
    await loginPage.login(users.customer.email, users.customer.password);
  });

 test("invalid user sign in",
    async ({page,loginPage}) => {
      await page.goto("/login");
      await loginPage.invalidLogin(
        "wrong@test.com",
        "wrong123"
      );
      await expect.soft(loginPage.errorMessage()).toContainText("Invalid credentials");
    });
  test("Search Product",
  async ({page,loginPage,searchPage}) => {
    await page.goto("/login");
    await loginPage.login(
      users.customer.email,
      users.customer.password
    );
    await page.goto("/catalog");
    await searchPage.search(searchKey);
    await expect(searchPage.results()).toHaveCount(1);
});

test('handle if there is No products found',async({
  searchPage,
  page
})=>{
await page.goto("/catalog");
    await searchPage.search('laptopxyzzz');
await expect(searchPage.noProductsMessage()).toBeVisible();
})

  test("Add Product To Cart",
    async ({ page, addToCart }) => {
      await page.goto("/catalog");
      await addToCart.addCart(
        searchKey,
        productName
      );
      await expect(addToCart.cartCount()).toHaveText("1");
      const results =await new AxeBuilder({ page })
            .analyze();
 
    const seriousViolations =
        results.violations.filter(
            violation =>
                violation.impact === 'critical' ||
                violation.impact === 'serious'
        );
 
    expect.soft(
        seriousViolations
    ).toEqual([]);
    });
   test("Checkout Product", async ({page,addToCart,checkoutPage}) => {
    await page.goto("/catalog");
    await addToCart.addCart(
      searchKey,
      productName
    );
    await expect(addToCart.cartCount()).toHaveText("1");
    await checkoutPage.proceedToCheckout();
    await checkoutPage.placeOrder();
    const results =await new AxeBuilder({ page })
            .analyze();
 
    const seriousViolations =
        results.violations.filter(
            violation =>
                violation.impact === 'critical' ||
                violation.impact === 'serious'
        );
 
    expect.soft(
        seriousViolations
    ).toEqual([]);
  });

  test("Order Validation", async({page,addToCart,checkoutPage,orderPage}) => {
    await page.goto("/catalog");
    await addToCart.addCart(
      searchKey,
      productName
    );
    await checkoutPage.proceedToCheckout();
    await checkoutPage.placeOrder();
    await expect(orderPage.orderSuccessMessage()).toBeVisible();
    await expect(orderPage.orderConfirmationMessage()).toContainText("confirmed");
    await expect(orderPage.viewOrdersButton()).toBeVisible();
});

});