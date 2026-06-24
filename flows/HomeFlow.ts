import {expect, Page } from "@playwright/test";
import { HomePage } from "../pages/HomePage";

export class HomeFlow {

    private readonly homePage: HomePage;

    constructor(private readonly page:Page) {
        this.homePage = new HomePage(page);
    }

   async verifyPage(){
    await this.homePage.goto();
    await this.homePage.verifyPageTitle();
   }

   async searchValidProduct(){
    await this.homePage.goto();
    await this.homePage.positiveSearchProduct();
   }

   async searchInvaildProduct(){
    await this.homePage.goto();
    await this.homePage.negativeSearchProduct();
   }

}
