import{test,expect} from "@playwright/test";
import { searchFlow } from "../flows/searchFlow";
test("Search a product",async({page})=>{
    const flow =new searchFlow(page);
    await page.goto("https://www.nykaa.com");
    await expect(page).toHaveURL("https://www.nykaa.com");
    await flow.searchFunc("+");
    await expect(page.getByText("Thanks for visiting our website!")).toBeVisible();
});