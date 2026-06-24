import {Page} from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { ResultPage } from "../pages/ResultPage";


export class ShopFlow{

    readonly homePage: HomePage
    readonly resultPage: ResultPage

    constructor(readonly page:Page){
        this.homePage = new HomePage(page)
        this.resultPage = new ResultPage(page)
    }

    async search_existing_product(product:string){
        await this.homePage.search(product);
        await this.resultPage.positive_search_result(product);
    }

    async search_non_existing_product(product:string){
        await this.homePage.search(product);
        await this.resultPage.negative_search_result(product);
    }
}