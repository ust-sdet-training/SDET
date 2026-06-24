import { Page ,expect} from "@playwright/test";

import { HomePage } from "../pages/HomePage";

export class HomeFlow{
    private readonly homePage:HomePage;
    constructor(private readonly page: Page){
        this.homePage = new HomePage(page);         
    }

    async searchProduct(productName:string){
        await this.homePage.goto();
        await this.homePage.searchProduct(productName);
        await expect(this.page).toHaveURL(/search/i);
    }
   
}