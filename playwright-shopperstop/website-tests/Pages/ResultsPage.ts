import {expect, Page} from "@playwright/test"


export class ResultPage{

    constructor(readonly page:Page){}

    async verifyPositiveSearchResult(prod:string){
        await expect(this.page.getByText(prod).first()).toBeVisible()   
    }

    async verifyNegativeSearchResult(prod:string){

        await expect(this.page.getByText("No Result Found")).toBeVisible();
        await expect(this.page.getByText("Looks like we couldn't find a match - give it another go.")).toBeVisible();
        await expect(this.page.getByRole("button", {name: "Explore & Shop"})).toBeVisible();

    }



}