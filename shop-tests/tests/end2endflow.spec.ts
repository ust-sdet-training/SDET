//flow without using pages and fixtures 

import { test,expect} from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";
import { LoginPage } from "../pages/LoginPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderFlow } from "../pages/OrderFlow";
import { testUsers } from "../fixtures/test-users";
import { products } from "../fixtures/products";

test.describe("Complete user journey", () => {
  test.beforeEach(async ({ page }) => { 
    await page.goto('/login');
  });

  test("complete journey from login ->placing order",async ({page})=>{
    const loginForm = page.getByRole("form", {name: "Login"});
    await expect(loginForm.getByLabel("Email")).toBeVisible();
    await expect(loginForm.getByLabel("Password")).toBeVisible();
    await expect(loginForm.getByLabel("Country")).toBeVisible();
    await expect(loginForm.getByLabel("Remember me")).toBeVisible();
    await expect(loginForm.getByRole("button", {name: "Sign in"})).toBeVisible();
    await loginForm.getByLabel("Email").fill(testUsers.invalid.email);
    await loginForm.getByLabel("Password").fill(testUsers.invalid.password);
    await loginForm.getByRole("button", {name: "Sign in"}).click();
    await expect(page.getByRole("alert")).toContainText(/invalid credentials/i);

    await loginForm.getByLabel("Email").fill(testUsers.locked.email);
    await loginForm.getByLabel("Password").fill(testUsers.locked.password);
    await loginForm.getByRole("button", {name: "Sign in"}).click();
    await expect(page.getByRole("alert")).toContainText(/account is locked/i);
    
    await loginForm.getByLabel("Email").fill(testUsers.customer.email);
    await loginForm.getByLabel("Password").fill(testUsers.customer.password);
    await loginForm.getByLabel("Country").selectOption({ label: "India" });
    await loginForm.getByLabel("Remember me").check();
    
    const loginResponse = page.waitForResponse(response =>response.url().includes("/login") && response.status() === 200);
    await loginForm.getByRole("button", {name: "Sign in"}).click();
    await loginResponse;
    await expect(page).toHaveURL("/home");
    await expect(page.getByText(/welcome/i)).toBeVisible();
    await expect(page.getByRole("button", {name: "Sign out"})).toBeVisible();

    await page.getByRole("link",{name:'Preview products'}).click();
    await expect(page).toHaveURL('/catalog');
    await expect(page.getByRole("heading", {name: "Product Catalog"})).toBeVisible();
    await expect(page.getByTestId("catalog-result-count")).toContainText("12");
    const searchResponse = page.waitForResponse(response =>response.url().includes("/products") && response.status() === 200);
    await page.getByRole("searchbox").fill("Running Shoes");
    await page.getByRole("button", {name: "Search"}).click();
    await searchResponse;
    await expect(page.getByRole("heading",{name:"Running Shoes"})).toBeVisible();
    await expect(page.getByRole("link", {name: /view running shoes/i})).toBeVisible();
    await expect(page.getByTestId("catalog-result-count")).toContainText("1");
    await page.getByRole("link", {name: /view running shoes/i}).click();
    await expect(page).toHaveURL('/product/running-shoes');
    await expect(page.getByRole("heading", {name: /running shoes/i})).toBeVisible();

   await expect.soft(page.getByRole("definition").filter({ hasText: "SwiftRun" })).toBeVisible();
   await expect.soft(page.getByText("Rs. 4,499")).toBeVisible();
   await expect.soft(page.getByText("18 in stock")).toBeVisible();
   await expect.soft(page.getByText("Ships tomorrow")).toBeVisible();
   await expect.soft(page.getByText("10-day easy exchange")).toBeVisible();
   await expect.soft(page.getByRole("combobox")).toHaveValue("UK 7");
   await expect.soft(page.getByRole("radio", {name: "Navy"})).toBeChecked();
   await expect.soft(page.getByRole("spinbutton")).toHaveValue("1");
   await expect.soft(page.getByRole("radio", {name: "Home delivery"})).toBeChecked();

   const addToCartResponse =page.waitForResponse(response =>response.url().includes("/cart") &&response.status() === 201);
   await page.getByRole("button", {name: /add to cart/i}).click();
   await addToCartResponse;

   await expect(page.getByRole("heading", {name: "Cart"})).toBeVisible();
   await expect.soft(page.getByText(/cart count/i)).toContainText("1");
   await expect.soft(page.getByText("Qty 1").first()).toBeVisible();
   await expect.soft(page.getByLabel("Shipping method")).toHaveValue("Standard shipping");
   await expect.soft(page.getByText("SUBTOTAL")).toBeVisible();
   await expect(page.getByRole("button", {name: /proceed to checkout/i})).toBeEnabled();

  await page.getByRole("button", {name: /proceed to checkout/i}).click();
  await expect(page).toHaveURL(/checkout/);
  await expect(page.getByRole("heading", {name: "Checkout"}).first()).toBeVisible();
  await expect.soft(page.getByLabel("Email")).toHaveValue("customer@example.com");
  await expect.soft(page.getByLabel("Delivery address")).toContainText("UST Campus");
  await expect.soft(page.getByLabel("Delivery slot")).toHaveValue("Tomorrow 9 AM - 12 PM");
  await expect.soft(page.getByLabel("Payment method")).toHaveValue("Credit card");
  await expect(page.getByRole("button", {name: /place order/i})).toBeEnabled();
  await page.getByRole("button", {name: /place order/i}).click();
  await expect(page.getByText("Thank you for your order")).toBeVisible();
  await expect.soft(page.getByText(/order ord-/i)).toBeVisible();
  await expect.soft(page.getByText(/confirmed/i)).toBeVisible();
  await expect.soft(page.getByRole("button", {name: /view orders/i})).toBeVisible();
  await page.getByRole("button", {
  name: /view orders/i
}).click();

await expect(page).toHaveURL(/orders/);

await expect(
  page.getByRole("heading", {
    name: /orders/i
  })
).toBeVisible();







   



    
    



})

// test("Searching the product using search bar" , async ({page})=>{
    
// })


});


