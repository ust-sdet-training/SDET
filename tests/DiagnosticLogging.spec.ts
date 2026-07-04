import { test, expect } from '../fixtures/artifacts';

test('End to End logging', async ({ evidence, page, log }) => {
  log.info("moving to the url")
  await page.goto('/catalog');

  log.info("clicking on the product page")
  await page.getByRole('link', { name: 'View Running Shoes' }).click();

  log.info("clicking on the add to cart page")
  await page.locator('[data-test="add-to-cart"]').click();

    log.info("adding the screenshot of the cart page")
  evidence.screenshot =   await page.screenshot({
                            fullPage: true
                        });

    var response = await page.request.get('http://localhost:4000/api/cart',{
        headers:{
            "Authorization":" Bearer demo-token-1-customer"
        }
    })

    evidence.cartData = await response.json()

  log.info("clicking on the checkout button")
  await page.locator('[data-test="checkout-button"]').click();

  log.info("Adding the coupon code")

    await page.getByRole('textbox', { name: 'Coupon code' }).click();
  await page.getByRole('textbox', { name: 'Coupon code' }).fill('UST10')

  log.info("clicking on the placeorderbutton button")
  await page.locator('[data-test="place-order"]').click();

    log.info("clicking on the view order button")
  await page.getByRole('button', { name: 'View orders' }).click();

  evidence.diagnosis = "Order on item placed"


});