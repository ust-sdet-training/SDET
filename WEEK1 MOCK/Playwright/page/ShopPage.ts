import {expect, Page} from"@playwright/test"
export class ShopPage{
    constructor(public readonly page:Page)
    {

    }
    async goto()
    {
        await this.page.goto('https://www.shoppersstop.com')
    }
    async search(itemname:string )
    {
       
        await this.page.getByRole("textbox",{name:"Search"}).fill(itemname)
        await this.page.getByRole("textbox",{name:"Search"}).press('Enter')
    }
}