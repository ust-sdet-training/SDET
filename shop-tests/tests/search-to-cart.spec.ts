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
