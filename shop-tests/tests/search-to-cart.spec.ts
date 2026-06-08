import{test,expect} from '@playwright/test';
import { testUsers } from '../fixtures/testUsers';
import { LoginPage } from '../pages/LoginPage';
import { SearchPage } from '../pages/SearchPage';
import { CartPage } from '../pages/CartPage';
import { ProductPage } from '../pages/ProductPage';

test.describe("Search to Cart Testing", ()=>{
    test("Testing Search in Catalog Page with existing value", async({page})=>{
        const searchPage = new SearchPage(page);
        await searchPage.open();;
        await searchPage.search("running");
        await expect(searchPage.results()).toHaveCount(1);
        });

    test("Testing Search in Catalog Page with random existing letter", async({page})=>{
        const searchPage = new SearchPage(page);
        await searchPage.open();
        await searchPage.search("t");
        await expect(searchPage.results()).toHaveCount(3);
        });
        
    test("Testing Search in Catalog Page with random not existing letter", async({page})=>{
        const searchPage = new SearchPage(page);
        await searchPage.open();
        await searchPage.search("z");
        await expect(searchPage.results()).toHaveCount(0);
        });
                
});

test.describe("View The Product Page", ()=>{
    test("The product from the Page is viewing", async({page}) =>{
        await page.goto('/');
        const productPage = new ProductPage(page);
        const searchPage = new SearchPage(page);
        searchPage.open();
        await searchPage.firstItem();
        // await productPage.goto();
        await expect.soft(page.getByLabel("Running Shoes")).toBeVisible();
        await productPage.addCart();
    })
})

// test.describe("Testing Add Products to cart", () =>{
//     test("Adding Products", async({page}) =>{
//         const cartPage = new CartPage(page);
//         await cartPage.goto();

//     });
// });