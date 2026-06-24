import {test, expect} from '@playwright/test';

test("Search valid product name ", async ({page}) => {
    await page.goto('https://www.nykaa.com/', {waitUntil: 'domcontentloaded'});
    await expect(page).toHaveTitle(/Nykaa/i);
    await page.getByPlaceholder('Search on Nykaa').fill('SHOAP');
    await page.keyboard.press('Enter');
    await expect(page.locator('h1')).toBeVisible();
    await expect(page.locator("h1")).toContainText(/SHOAP/i);
    
    await expect(page.locator("id=product-list-wrap")).toBeVisible();      

});

test("Search invalid product word in search field and validate the expected result in page", async ({page}) => {
    await page.goto('https://www.nykaa.com/', {waitUntil: 'domcontentloaded'});
    await page.getByPlaceholder('Search on Nykaa').fill('adadadads');
    await page.keyboard.press('Enter');
    
    await expect(page.locator('.subtitle')).toBeVisible();

    await expect(page.locator('.subtitle'))
    .toContainText("Unfortunately, we couldn’t find any matches for qweqwqdqad. Please try searching with another term.");
});
