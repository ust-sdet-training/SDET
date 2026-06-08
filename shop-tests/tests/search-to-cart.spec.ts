// import {test, expect} from "@playwright/test";
// import { SearchPage } from "../pages/SearchPage";
// import { LoginPage } from "../pages/LoginPage";
// import { testUsers } from "../fixtures/test";


// test.describe("Search to Cart Cases", ()=>{

//     test("Login Page Case",async ({page})=>{
//         await page.goto("/login");
//         // await expect(page.getByRole('heading', {name: "SDET Retail Automation Lab"})).toBeVisible();
    
//         // const Loginform = page.getByRole('form', {name: 'Login'})
//         // await Loginform.getByLabel("Email").fill(testUsers.customer.email);
//         // await Loginform.getByLabel("Password").fill(testUsers.customer.password);
//         // await Loginform.getByRole("button", {name: "Sign in"}).click();
//         // await expect(page).toHaveURL('/login');
//         // const loginCheck = new LoginPage(page);
//         // await loginCheck.goto();
//         // await loginCheck.login(testUsers.customer.email, testUsers.customer.password);
//         // await loginCheck.expectError("Invalid Login")

//     })

//     test("Search Case",async ({page})=>{

//     })

//     // test("Product Page Check", async({page})=>{

//     // })
// })

import { test } from "@playwright/test";
import { products, testUsers } from "../fixtures/test";
import { ShopFlow } from "../flows/ShopFlow";
 
test.describe("search-to-cart", () => {
    test("Flow Check", async ({page}) => {
        await page.goto('/login');
 
        const shopFlow = new ShopFlow(page);
        // shopFlow.Login(testUsers.customer.email,testUsers.customer.password);
       
        shopFlow.buyProduct(testUsers.customer.email,testUsers.customer.password,products.footwear[0].name);
 
    })
   
 
});
 