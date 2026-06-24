import {test, expect} from "@playwright/test";


test('Open page', async({page}) => {
    await page.goto("https://www.nykaa.com/");
    await expect(page.getByRole("img", {name:"Nykaa logo"})).toBeVisible();

});

// test('Search Product', async({page}) => {

//     await page.goto("https://www.nykaa.com/")
//     await page.getByRole('textbox', {name:"Search on Nykaa"}).click();
//     await page.getByRole('textbox', {name: "Search on Nykaa"}).fill('lipstick');
//     await page.getByRole('textbox',{name: "Search on Nykaa"}).press('Enter');
//     await expect(page.getByRole('heading', {name: "Best Lip Makeup"})).toBeVisible();

//     await page.getByRole('img',{name: 'liquid-lipstick'}).click();
//     await expect(page.getByRole('heading', {name: 'Buy Liquid Lipstick'})).toBeVisible();
//     await page.getByRole('button',{name:'Preview Shades'}).first().click();
//     await page.getByRole('img',{ name: 'Boldlady Brick-Shade'}).click();
//     await page.getByRole('button',{name: 'Add to bag Lakme 9to5'}).click();
//     await expect(page.getByRole('button',{name: 'header-bag-icon'})).toHaveCount(1);

// })