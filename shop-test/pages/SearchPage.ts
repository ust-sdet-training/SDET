import {expect,type Page} from '@playwright/test'

export class SearchPage{
    constructor (private readonly page:Page){}
    button=()=>this.page.getByRole("button",{name:"search"})
    card=()=>this.page.getByTestId("catalog-result-count")
    async search(name:string)
    {
        
        await this.page.getByPlaceholder("Search by product name").fill(name)
        await this.page.getByLabel("category").selectOption("Footwear")
        await expect(this.page.getByLabel("Sort By")).toHaveValue("Recommended")
    }
    async click_button()
    {
        const api_prod=this.page.waitForResponse(
        r=>r.url().includes('api/products') &&
        r.status()===200)
        await this.page.getByRole('button',{name:'Search'}).click()
        const pcart= this.page.getByTestId('product-cart')
        await pcart.isVisible()
        this.page.waitForTimeout(2000)
        await this.page.getByRole('link',{name:/view/i}).first().click()
        await api_prod

        

    }

}