import { Page ,expect} from "@playwright/test";

import { HomePage } from "../pages/HomePage";

export class HomeFlow{
    private readonly homePage:HomePage;
    constructor(private readonly page: Page){
        this.homePage = new HomePage(page);         
    }

    async gotoHomePage(){
        await this.homePage.goto();
        await this.page.waitForURL(/nykaa/i);
        await expect(this.page).toHaveURL(/nykaa/i);
        await expect(this.page.getByRole("img", {name: /nykaa logo/i})).toBeVisible();
    }

    async searchvalidProduct(productName:string){
        await expect(this.page).toHaveURL(/nykaa/i);

        await this.homePage.searchProduct(productName);

        await this.page.waitForURL(/search/i);
        await expect(this.page).toHaveURL(/search/i);

        await expect(this.page.getByRole("heading", {name: /Buy Shampoos Online, 1380/i})).toBeVisible();
        await expect(this.page.getByRole("heading", {name: /Buy Shampoos Online, 1380/i})).toContainText(/shampoo/i);
    }

    async searchinvalidProduct(productName:string){
        await expect(this.page).toHaveURL(/nykaa/i);

        await this.homePage.searchProduct(productName);

        await this.page.waitForURL(/search/i);
        await expect(this.page).toHaveURL(/search/i);

        await expect(this.page.getByText('Thanks for visiting our website!Unfortunately, we couldn’t find any matches for')).toBeVisible();
        await expect(this.page.getByRole("button", {name: /Back to Home/i})).toBeVisible();
    }
   
}