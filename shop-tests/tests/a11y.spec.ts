import { test,expect } from "@playwright/test";
import { products, testUsers } from "../fixtures/test";
import { ShopFlow } from "../flows/ShopFlow";
import AxeBuilder from '@axe-core/playwright';
 
test.describe("Accessiblility", () => {
    test("Check", async ({page}) => {
        for(const path of ['/login', '/catalog', '/cart', '/checkout']) {
        await page.goto(path);
 
        const accessibilityScanResults = await new AxeBuilder({page}).analyze();
 
        expect(accessibilityScanResults.violations).toEqual([]);
    }
    })
   
});
 