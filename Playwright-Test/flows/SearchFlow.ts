import {type Page, expect} from "@playwright/test"
import { HomePage } from "../pages/HomePage";

export async function SearchFlow(page: Page, query: string){
        const homePage = new HomePage(page);
        await homePage.goto();
        await expect(page).toHaveTitle("Buy Cosmetics Products & Beauty Products Online in India at Best Price | Nykaa");

        await homePage.search(query);
        await expect(page.getByRole("heading", {name: query})).toBeVisible;
      
}
