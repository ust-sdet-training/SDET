import {test, expect} from '@playwright/test';
import {Login} from '../pages/Login';
import {SerchPage} from '../pages/SerchPage';
import {ProductPage} from '../pages/ProductPage';
import {CheckoutPage} from '../pages/CheckoutPage';

test('Assessment', async ({ page }) => {

    const login = new Login(page);
    const searchPage = new SerchPage(page);
    const productPage = new ProductPage(page);
    const checkoutPage = new CheckoutPage(page);

    await login.login('user@test.com','Secret123');
    await expect(page.getByRole('heading',{name:'Welcome'})).toBeVisible();
    await searchPage.search('Running Shoes');
    await searchPage.openProduct('Running Shoes');
    await productPage.selectSize('UK 9');
    await productPage.addToCart();
    await checkoutPage.checkout();
    await checkoutPage.placeOrder();
    await expect(page.getByRole('heading',{name:'Checkout'})).toBeVisible();
});