import {Page, expect} from '@playwright/test';

export class HomePage{
    constructor(readonly page:Page){}

    private Search_bar = this.page.getByPlaceholder("Search on Nykaa");


    async search(product:string){
        await this.page.goto("/?root=logo", {waitUntil: "domcontentloaded"})
        await expect(this.page).toHaveURL("https://www.nykaa.com/?root=logo")
        await expect(this.page).toHaveTitle(/Buy Cosmetics Products & Beauty Products Online in India at Best Price | Nykaa/)
        
        await expect(this.page.getByRole('img', { name: 'Nykaa Logo' })).toBeVisible()

        await this.Search_bar.fill(product)
        await expect(this.Search_bar).toHaveValue(product)
        await this.Search_bar.press("Enter")
    }

}