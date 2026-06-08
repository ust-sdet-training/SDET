import { test, expect } from "@playwright/test"
import { LoginPage } from "../pages/LoginPage"
import { testUsers } from "../fixtures/test-users";
import { HomePage } from "../pages/HomePage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderPage } from "../pages/OrderPage";

test.describe("Assessment 1", async() => {
    test("login to cart", async ({ page }) => {
        const loginPage = new LoginPage(page);
        const homePage = new HomePage(page);
        const searchPage = new SearchPage(page);
        const productPage = new ProductPage(page);
        const cartPage = new CartPage(page);
        const checkoutPage = new CheckoutPage(page);
        const orderPage = new OrderPage(page);

        await loginPage.goto();
        await expect(page).toHaveURL(/\/login$/);

        await loginPage.login(testUsers.customer.email, testUsers.customer.password);
        await loginPage.success();
        await expect(page).toHaveURL(/\/home$/);

        await homePage.productsPage();
        await searchPage.search('run');
        await expect(searchPage.resultCount()).toContainText(' 1 ');
        await expect(searchPage.cardCount()).toHaveCount(1);


        
        await searchPage.cardCount().first().getByRole('link', {name: /view/i}).click();
        await expect(page).toHaveURL(/\/running-shoes/);
        await productPage.add_cart();

        await expect(page).toHaveURL(/\/cart/);
        await expect(cartPage.cartCount()).toHaveText('1');
        await cartPage.checkout();
        await expect(page).toHaveURL(/\/checkout/);

        await checkoutPage.order();
        await page.getByRole("button", {name: "View orders"}).click();

        const orderNo = await CheckoutPage.orderNo as unknown as string;
        await orderPage.checkForOrder(orderNo);

    })

    test("login failure", async ({page}) =>{
        const loginPage = new LoginPage(page);
        await loginPage.goto();
        await expect(page).toHaveURL(/\/login$/);

        await loginPage.login(testUsers.invalid.email, testUsers.invalid.password);
        await loginPage.expectError("Invalid credentials");
    })

    test("search failure", async ({page}) => {
        const searchPage = new SearchPage(page);
        
        await searchPage.goto();
        await searchPage.search('abcdef');
        await expect(searchPage.resultCount()).toContainText(' 0 ');
        await expect(searchPage.cartCount()).toHaveCount(0);
    })

    // test("buying more than stock", async ({page}) => {
    //     const loginPage = new LoginPage(page);
    //     const homePage = new HomePage(page);
    //     const searchPage = new SearchPage(page);
    //     const productPage = new ProductPage(page);

    //     page.goto("product/running-shoes");


    // })

    
})