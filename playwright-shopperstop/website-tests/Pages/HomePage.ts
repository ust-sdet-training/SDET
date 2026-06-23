import { expect, Page } from "@playwright/test"

export class HomePage{

    constructor(readonly page:Page){}

    async search(prod:string){
        let search = this.page.locator("input")
        await this.page.goto('/home', {waitUntil:"domcontentloaded"})
        await expect(this.page).toHaveTitle(/Shoppers Stop/)
        await expect(search).toBeVisible()
        await search.fill(prod)
        await search.press("Enter")
}


        
}