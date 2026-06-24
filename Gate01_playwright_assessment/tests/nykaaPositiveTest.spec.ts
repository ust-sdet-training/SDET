import{test,expect} from "@playwright/test";
import { searchFlow } from "../flows/searchFlow";
test("Search a product",async({page})=>{
    const flow =new searchFlow(page);
    await page.goto("https://www.nykaa.com");
    await expect(page).toHaveURL("https://www.nykaa.com");
    await flow.searchFunc("Shirts");
    await flow.viewProduct();
    await expect(page.getByText("MINI KLUB")).toBeVisible();
});