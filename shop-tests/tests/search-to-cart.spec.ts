import { test, expect } from '@playwright/test';
 import { AddtocartPage } from '../pages/AddtocartPage';
import { SearchPage } from '../pages/SearchPage';
import { CheckoutPage } from '../pages/CheckoutPage';


test("Login page",async({page}) => {
    await page.goto('/login');

    await page.getByLabel("Email").fill("user@test.com");
    await page.getByLabel("password").fill("Secret123");
    await page.getByRole("button",{name:'Sign in'}).click();
    await expect(page.getByRole("heading",{name:'Welcome'})).toBeVisible();
})


test('search + add to cart', async ({ page }) => {
     await page.goto('/catalog');

  const searchPage = new SearchPage(page);
  await searchPage.search('Running Shoes');

  await expect(page.getByRole('heading', { name: 'Running Shoes' })).toBeVisible();
})


 test('add to cart', async ({ page }) => {

  await page.goto('/catalog');
   const cartPage = new AddtocartPage(page);
   await cartPage.addItem();
   await expect(page.getByTestId('cart-count')).toHaveText('1');
});



test('simple checkout flow', async ({ page }) => {

  await page.goto('/catalog');

  const cartPage = new AddtocartPage(page);
  await cartPage.addItem();

  const checkoutPage = new CheckoutPage(page);
  await checkoutPage.goToCheckout();
  //await expect(page.getByRole('heading', { name:'Checkout' })).toBeVisible();
  
   await expect( page.locator('#checkout-title')).toBeVisible();

  await page.getByRole('button',{name: 'Place order'}).click();
});


test("for orders",async({page}) => {
    await page.goto('/catalog');

  const cartPage = new AddtocartPage(page);
  await cartPage.addItem();

  const checkoutPage = new CheckoutPage(page);
  await checkoutPage.goToCheckout();
  await page.getByRole('button',{name: 'Place order'}).click();
  await page.getByRole('button',{name:'View orders'}).click();

});








