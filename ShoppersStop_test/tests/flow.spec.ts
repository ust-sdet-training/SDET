import { test, expect } from '../fixtures/fixtures';
import { ProductPage } from '../pages/ProductPage';

test.describe('Shoppers Stop Search Flow', () => {

    test('Verify search and filter functionality', async ({
        homePage,searchPage}) => {

        await homePage.navigate();
        await homePage.verifyTitle();
        await homePage.searchProduct("Watches");
        await searchPage.verifySearchResults("Watches");
        await searchPage.applyWomenFilter();
        const popup = await searchPage.openFirstProduct();
        const productPage = new ProductPage(popup);
        await productPage.verifyProductPage();
        await expect(popup.locator("body")).toContainText(/WATCH|GUESS|TITAN|FOSSIL/i);
    });

});