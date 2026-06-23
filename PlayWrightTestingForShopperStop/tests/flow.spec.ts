import {expect,test} from "@playwright/test";

test("Verify flow", async ({ page }) => {
  await page.goto('https://www.shoppersstop.com/');
  await expect(page.getByRole('link', { name: 'logo', exact: true })).toBeVisible();
  await page.getByRole('textbox', { name: 'Search' }).click();
  await page.getByRole('textbox', { name: 'Search' }).fill('shoes');
  await page.getByRole('textbox', { name: 'Search' }).press('Enter');
  //await expect(page.getByRole('heading', { name: 'Shoes' })).toBeVisible();
  const page1Promise = page.waitForEvent('popup');
  await page.getByTestId('product-card').first().click();
  const page1 = await page1Promise;
});

test("Search for a product which is not present",async({page})=>{
   await page.goto('https://www.shoppersstop.com/');
  await page.getByRole('textbox', { name: 'Search' }).click();
  await page.getByRole('textbox', { name: 'Search' }).fill('lahari gandla');
  await page.getByRole('textbox', { name: 'Search' }).press('Enter');
  await page.getByText('No Result Found');
  await page.getByText('Looks like we couldn\'t find a');

})