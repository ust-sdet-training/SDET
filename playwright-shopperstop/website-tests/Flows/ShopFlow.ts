import { HomePage } from "../Pages/HomePage";
import { ResultPage } from "../Pages/ResultsPage";
import { Page } from "@playwright/test";

export class ShopFlow{

    readonly homePage:HomePage;
    readonly resultPage:ResultPage;

    constructor( readonly page:Page){
        this.homePage=new HomePage(page);
        this.resultPage=new ResultPage(page);
    }

    async searchPositiveProduct(prod:string){
        await this.homePage.search(prod);
        await this.resultPage.verifyPositiveSearchResult(prod);
    }

    async searchNegativeProduct(prod:string){
        await this.homePage.search(prod);
        await this.resultPage.verifyNegativeSearchResult(prod);
    }
}