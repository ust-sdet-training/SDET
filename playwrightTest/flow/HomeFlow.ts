import { expect, Page, test } from "@playwright/test";
import { HomePage } from "../pages/HomePage";


export class HomeFlow {
    private readonly homePage:HomePage;
    constructor(private readonly page:Page){
       this.homePage = new HomePage(page);
    }

   async check(query:string){
        await this.homePage.goto();
        await this.homePage.search(query);
   }


}