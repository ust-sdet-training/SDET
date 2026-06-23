import {expect,Page} from "@playwright/test"
import {ShopPage} from "../page/ShopPage"
export class ShopFlow{

    private readonly shoppage:ShopPage;
    constructor (private readonly page:Page)
    {
        this.shoppage=new ShopPage(page)

    }
    async possearch(name:string)
    {
        await this.shoppage.goto()
        await expect(this.page.getByRole("img",{name:"logo"})).toBeVisible
        await this.shoppage.search(name)
        await expect(this.page.getByRole("heading",{name:"Shoes"})).toBeVisible
        await expect(this.page.getByRole("img",{name:"product card"})).toBeVisible 

    }
    async negsearch(name:string)
    {
        await this.shoppage.goto()
        await expect(this.page.getByRole("img",{name:"logo"})).toBeVisible
        await this.shoppage.search(name)
        await expect(this.page.getByRole("img",{name:"no-result-found"})).toBeVisible
    }

}