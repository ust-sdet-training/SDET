import {test,expect} from '@playwright/test';
import {HomePage} from '../pages/HomePage';
import {LoginPage} from '../pages/LoginPage';
import {CatalogPage} from '../pages/CatalogPage';
import {ProductPage} from '../pages/ProductPage';
import {CartPage} from '../pages/CartPage';
import {CheckoutPage} from '../pages/CheckOutPage';
import {OrdersPage} from '../pages/OrdersPage';
import { creds } from '../test-data/loginCredentials';
import {products} from '../test-data/productData';
 
test('Product Search and Order Funtionalitiy Test',async({page})=>{
 
    const homePage = new HomePage(page);
    await homePage.goToHomePage();
    expect(await homePage.getTitleHeading()).toBe('SDET Retail Automation Lab');
    await homePage.clickOnSignInButton();
 
    const loginPage = new LoginPage(page);
    await loginPage.login(creds.customer.email,creds.customer.password,creds.customer.country);
 
    await expect(page).toHaveURL(/home/);
    expect(await homePage.getTitleHeading()).toBe('Welcome, Customer User');
    await homePage.clickOnPreviewProducts();
 
    const catalogPage = new CatalogPage(page);
    await expect(page).toHaveURL(/catalog/);
    expect(await catalogPage.isCatalogTitleHeadingVisible()).toBeTruthy();
 
    const product = products.runningShoes
    await catalogPage.searchProduct(product.name);
    await catalogPage.clickOnSearchButton();
    expect(await catalogPage.getProductCountText()).toBe('Showing 1 product');
    await catalogPage.viewProduct(product.name);
 
 
    const productPage = new ProductPage(page);
    expect(await productPage.getProductHeading(product.name)).toBe(product.name);
    await productPage.selectSize(product.size);
    await productPage.selectColor(product.color);
    await productPage.selectQuantity(product.quantity);
    await productPage.selectfulfilment(product.fulfilment);
    await productPage.clickAddToCartButton();
 
    const cartPage = new CartPage(page);
    await expect(page).toHaveURL(/cart/);
    expect.soft(await cartPage.getCartCount()).toBe('4');
    await cartPage.clickOnProceedToCheckoutButton();
 
    const checkoutPage = new CheckoutPage(page);
    await expect(page).toHaveURL(/checkout/);
    expect(await checkoutPage.isCheckoutTitleHeadingVisible()).toBeTruthy();
    await checkoutPage.clickOnPlaceOrderButton();
 
    expect(await checkoutPage.isConfirmationTextDisplayed()).toBeTruthy();
    await checkoutPage.clickOnViewOrdersutton();
 
    const ordersPage = new OrdersPage(page);
    await expect(page).toHaveURL(/orders/);
//  expect(await ordersPage.isOrderPresent()).toBeTruthy();
 
})