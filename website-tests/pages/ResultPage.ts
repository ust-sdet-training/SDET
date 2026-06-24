import {expect, Page} from "@playwright/test"

export class ResultPage{
    constructor(readonly page:Page){}

    async positive_search_result(product:string){
        await expect(this.page).toHaveURL(/search/)
        await expect(this.page.locator(".result-count")).toContainText("Showing")
    }

    async negative_search_result(product:string){
        await expect(this.page).toHaveURL(/search/)
        await expect(this.page.getByRole("img", {name: "no_result"})).toBeVisible()
        await expect(this.page.locator(".title")).toContainText("Thanks for visiting our website!")
        await expect(this.page.locator(".subtitle")).toContainText("Unfortunately, we couldn’t find any matches")
        await expect(this.page.getByRole("button", {name: "Back to Home"})).toBeVisible()
    }


    
}