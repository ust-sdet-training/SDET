import {test,expect} from '../fixtures/test'

test('test for nykaa home page visible  ', async ({ page,home }) => { 
        await home.navigateToHomePage();
        await expect(page).toHaveURL('https://www.nykaa.com/');
        await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
        await expect(page.locator('.image-wrapper.css-19k52lt > .css-13o7eu2')).toBeVisible();
});

test("test for nykaa search box visible and search for positive item", async ({ page,home }) => {
  await home.positiveSearch('Shirt');
  await expect(page).toHaveURL(/search\/result\/\?q=Shirt/i);
   await expect(page.getByText('Showing 20 of 279 results for')).toBeVisible();
  await expect(page.getByRole('heading', { name: 'shirt for man' })).toBeVisible();
});


test("test for nykaa search box visible and search for negative item", async ({ page,home }) => {
  await home.negativeSearch('adbduidsjhksdfksfksdj');
  await expect(page).toHaveURL(/search\/result\/\?q=adbduidsjhksdfksfksd/i)
  await expect(page.getByRole('img', { name: 'no_result' })).toBeVisible();
  await expect(page.getByText('Thanks for visiting our website!Unfortunately, we couldn’t find any matches for')).toBeVisible();
});

test("test for nykaa search box visible and search for negative item with empty", async ({ page,home }) => {
  await home.negativeSearch('');
  await expect(page.getByText('Thanks for visiting our website!Unfortunately, we couldn’t find any matches for')).toBeVisible();
});
