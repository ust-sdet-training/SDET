import { test, expect } from "../fixtures/pages.fixture";

test("user searches for a product and relevant products are listed", async ({ page, homePage, searchPage }) => {
    await homePage.open();
    await expect(page).toHaveURL("https://www.shoppersstop.com/");
    await expect(page).toHaveTitle(/Shoppers Stop/i);

    await searchPage.searchproduct("shirts");
});

test("user sees no results for an invalid product search", async ({ page, homePage, searchPage }) => {
    await homePage.open();

    await searchPage.searchproduct("zzzzzz");

    
});

test("customer can open product and add it to bag", async ({ page, homePage, searchPage, productPage }) => {
    await homePage.open();
    await searchPage.searchproduct("shirts");
   

    await productPage.addtobag();
});
