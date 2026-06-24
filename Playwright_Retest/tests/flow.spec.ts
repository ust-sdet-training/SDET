import { test, expect } from "../fixtures/pagesFixtures";
import { product } from "../utils/testData"

test("user searches for a product and relevant products are listed", async ({ page, homepage, searchpage,productpage}) => {
    await homepage.open();
    await expect(page).toHaveURL("https://www.nykaa.com/");
    await expect(page).toHaveTitle(/Nykaa/i);
    await searchpage.search(product.productname);
    await expect(page.getByRole('heading', { name: 'Section Title: Shop By Colour' })).toBeVisible();
     await expect(page.getByRole('link', { name: 'Nykaa Lip Grip No-Transfer' })).toBeVisible();
    await productpage.addtobag();
//         const results =
//         await new AxeBuilder({
//             page
//         }).analyze();

//     expect(
//         results.violations
//     ).toEqual([]);
// });

   


test("user sees no results for an invalid product search", async ({ page, homepage, searchpage }) => {
    await homepage.open();
    await searchpage.search(product.invalidProduct);
    await page.getByText('Thanks for visiting our').click();
    await expect(page.getByText(/thanks for visiting/i)).toBeVisible();
    await expect(page.getByRole('button', { name: 'Back to Home' })).toBeVisible();
    
});

test("customer can open product and add it to bag", async ({ page, homepage, searchpage, productpage }) => {
    await homepage.open();
    await searchpage.search(product.productname);
    await productpage.addtobag();
});

