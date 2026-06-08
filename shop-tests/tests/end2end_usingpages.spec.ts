import { test, expect } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { testUsers } from "../fixtures/test-users";
import { products } from "../fixtures/products";
import AxeBuilder from '@axe-core/playwright';

test.describe("Customer Purchase Journey", () => {
    test("customer can purchase a product successfully", async ({ page }) => {
        const Productchosen = products[0];
        const loginPage = new LoginPage(page);
        const searchPage = new SearchPage(page);
        const productPage = new ProductPage(page);
        const cartPage = new CartPage(page);
        const checkoutPage = new CheckoutPage(page);
        
        await loginPage.open();
        await loginPage.login(testUsers.customer.email,testUsers.customer.password);
        await expect(page).toHaveURL(/home/);

        await page.getByRole("link", {name: /preview products/i}).click();
        await expect(page).toHaveURL(/catalog/);
        await searchPage.search(Productchosen.name);
        await expect(searchPage.results()).toContainText("1");
        
        await productPage.openProduct();
        await expect(page.getByRole("heading", {name: Productchosen.name})).toBeVisible();
        await expect.soft(page.getByText(Productchosen.brand).first()).toBeVisible();

        await expect.soft(page.getByText(/in stock/i)).toBeVisible();
        await expect.soft(page.getByText(/ships tomorrow/i)).toBeVisible();
        
        await productPage.addToCart();
        await expect(page.getByRole("heading", {name: "Cart"})).toBeVisible();
        await expect.soft(page.getByText(/cart count/i)).toContainText("1");
        await expect.soft(page.getByText("Qty 1").first()).toBeVisible();
        await expect(page.getByRole("button", {name: /proceed to checkout/i})).toBeEnabled();
    
        await cartPage.proceedToCheckout();
        await expect(page).toHaveURL(/checkout/);
        await expect(page.getByRole("heading", {name: "Checkout",exact: true})).toBeVisible();

        await expect.soft(page.getByText(/subtotal/i)).toBeVisible();
        await expect.soft(page.getByText(/shipping/i)).toBeVisible();
        await expect.soft(page.getByText("Total",{exact:true})).toBeVisible();
        await expect(page.getByRole("button", {name: /place order/i})).toBeEnabled();
        await checkoutPage.placeOrder();

        await expect(page.getByText("Thank you for your order")).toBeVisible();
        await expect.soft(page.getByText(/confirmed/i)).toBeVisible();
        await expect.soft(page.getByText(/ord-/i)).toBeVisible();


        await page.getByRole("link", {name: /orders/i}).click();
        await expect(page).toHaveURL(/orders/);
        await expect(page.getByRole("heading", {name: /orders/i})).toBeVisible();

        const results =await new AxeBuilder({ page }).analyze();
        const seriousViolations =results.violations.filter(violation =>violation.impact === 'critical' ||violation.impact === 'serious');
        expect.soft(seriousViolations).toEqual([]);


 
    });

    test("catalog accessibility scan", async ({ page }) => {
        await page.goto("/catalog");
        const results = await new AxeBuilder({ page }).analyze();
        console.log(results.violations);
        expect(results.violations).toEqual([]);
});

test("Checkout page accessibility",async ({page})=>{

const accessibilityResults = await new AxeBuilder({ page })
  .include(".checkout-page")
  .analyze();

expect(accessibilityResults.violations).toEqual([]);
});
});