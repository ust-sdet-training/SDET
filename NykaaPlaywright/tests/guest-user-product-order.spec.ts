import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import { ProductListingPage } from '../pages/ProductListingPage';
import { AuthPage } from '../pages/AuthPage';
import { AddressPage } from '../pages/AddressPage';
import { PaymentPage } from '../pages/PaymentPage';
import { guestUser } from '../test-data/guestUserdata';
import { product } from '../test-data/product';



test.only('Guest User Product Order Flow', async ({ page }) => {

    const homePage = new HomePage(page);
    await homePage.goToNykaa();
    expect(await homePage.isHomePageDisplayed()).toBeTruthy();
    await homePage.searchProduct(product.search);

    const productListingPage = new ProductListingPage(page);
    await productListingPage.isProductListingPageDisplayedWithProducts(product.search);
    await productListingPage.hoverToProductCard(product.card)
    await productListingPage.addProductToBag(product.productName);
    await productListingPage.clickAddedToBag();
    await productListingPage.clickProceedButton();

    const authPage = new AuthPage(page);
    await authPage.clickContinueAsGuestButton();

    const addressPage = new AddressPage(page);
    await addressPage.fillAddress(guestUser.pincode,guestUser.houseOfficeFlatNo,guestUser.roadAreaColony,guestUser.name,guestUser.phone,guestUser.email);
    await addressPage.clickShipToThisAddress();

    const paymentPage = new PaymentPage(page);
    await paymentPage.isPaymentPageHeadingVisible();
 
});
