import { AxeBuilder } from '@axe-core/playwright';
import { AddCart } from '../pages/AddCart';
import { test,  } from "../fixtures/shopFixture";
import { expect } from '@playwright/test';

test('valid login user', async ({
    login,
    page
}) => {
     await login.goto();
    await expect.soft(page.getByRole('heading',{name:'Sign in to Retail Lab'})).toBeVisible(); 
    await login.login('customer@example.com', 'Password@123');  
});

test("invalid user sign in",async({
    login,
    page
})=>{
    await login.goto();
    await login.login('wrong@test.com', 'wrong123');
    await expect(page.getByText('Invalid credentials')).toBeVisible();
    await expect(page).toHaveURL('/login');
});

test("catalog page searchng abt product",async({
search,
page
})=>{
    await search.goto();
    await search.searchProduct('Running Shoes');
   await expect.soft(page.getByRole('heading',{name:'Product Catalog'})).toBeVisible();
   await expect.soft(search.result()).toBeEnabled();
   await expect.soft(search.result() ).toBeEnabled();
   await search.openProduct('View Running Shoes');
})

test('handle if there is No products found',async({
  search,
  page
})=>{
await search.goto();
 await search.searchProduct('laptopxyzzz');
await expect(search.noProductsMessage()).toBeVisible();
})

test ('addto cart',async({
addcart,
search
})=>{
await search.goto();
 await addcart.goto();
 await addcart.addCart();
 await expect(addcart.stockerror()).not.toBeVisible();
 await expect(addcart.cartCount()).toBeVisible();
 await expect(addcart.count()).toHaveText('1');
})

test('proceed to checkout', async ({
    page,
    checkout,
    login,
    search,
    addcart,
    order
}) => {
  await login.goto();
  await search.goto();

await search.searchProduct('Running Shoes');
await search.openProduct('View Running Shoes');

  await addcart.addCart();
  await expect(
    addcart.count()
).toHaveText('1');
await checkout.checkout();

  await expect(page).toHaveURL('/checkout');
  await checkout.placeOrder();
  
await expect(
    order.orderSuccessMessage()
).toBeVisible();

await expect.soft(
    order.orderConfirmationMessage()
).toBeVisible();

await expect.soft(
    order.viewOrdersButton()
).toBeVisible();


const results =await new AxeBuilder({ page })
            .analyze();

    const seriousViolations =
        results.violations.filter(
            violation =>
                violation.impact === 'critical' ||
                violation.impact === 'serious'
        );

    expect.soft(
        seriousViolations
    ).toEqual([]);
});

