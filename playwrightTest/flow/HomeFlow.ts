import { expect, Page, test } from "@playwright/test";
import { HomePage } from "../pages/HomePage";


export class HomeFlow {
    private readonly homePage:HomePage;
    constructor(private readonly page:Page){
       this.homePage = new HomePage(page);
    }

   async checkHomePageLoaded(){
    await this.homePage.goto();
    await expect(this.page).toHaveURL("https://www.nykaa.com/");
    await expect(this.page).toHaveTitle("Buy Cosmetics Products & Beauty Products Online in India at Best Price | Nykaa");
   }

   async searchValidProduct(productName:string){
    await this.homePage.goto();
    await this.homePage.search(productName);
    await expect(this.page).toHaveURL(/search/);
    await expect(this.page.getByRole('heading', { name: productName, exact: true })).toBeVisible();
   }

   async searchInValidProduct(productName:string){
    await this.homePage.goto();
    await this.homePage.search(productName);
    await expect(this.page).toHaveURL(/search/);
    await expect(this.page.getByText("Thanks for visiting our website!")).toBeVisible();
   }

}