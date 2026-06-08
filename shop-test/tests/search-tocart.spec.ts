import {test, expect} from "../fixtures/test";
import { testUsers } from "../fixtures/test-users";
import { Page } from "@playwright/test";


test.describe("Search to Cart", ()=>{
    test("Login to View Order (HAPPY Flow): ", async ({page, loginPage, homePage, searchPage, productPage, cartPage, checkoutPage , orderPage})=>{
        // await page.goto("/");
        // await expect(page.getByRole("heading", {name:"SDET Retail Automation Lab"})).toBeVisible();
    
        //LOGIN ->(VALIDATED) -> HOME
        await loginPage.open();
        await expect(page).toHaveURL("/login");

        await loginPage.login(testUsers.customer.email, testUsers.customer.password);
        await expect(page.locator("#page-title")).toContainText(testUsers.customer.displayName);

        //HOME -> PRODUCT/CATALOG
        await expect(page).toHaveURL("/home");
        await homePage.previewProduct();

        //CATALOG -> [SEARCH] -> PRODUCT PAGE
        await expect(page).toHaveURL(/\/catalog$/);

        await searchPage.search("runn");

        await expect(searchPage.results()).toContainText('1');

        await searchPage.viewFirstProd();

        //PRODUCT PAGE -> CART
        await expect(page.getByRole('heading',{name:'product-title'}));

        await productPage.addProduct();

        await expect(productPage.results).not.toBe('1');

        //CART -> CHECKOUT
        await expect(cartPage.productPresent).not.toBe(0);

        await cartPage.proceedToCheckout();

        //CHECKOUT -> ORDERS
        await expect(page).toHaveURL(/\/checkout$/);

        await checkoutPage.placeOrder();

        const orderid=checkoutPage.orderID();

        await checkoutPage.vieweOrder();

        //ORDER
        await expect(page).toHaveURL(/\/orders$/);

        await expect(orderPage.orderIdinOrders().first()).toContainText(await orderid);


    })
})