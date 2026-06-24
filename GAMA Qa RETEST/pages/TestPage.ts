import {expect, Page} from"@playwright/test"
export class TestPage{
    constructor(public readonly page:Page)
    {

    }
    async goto()
    {
        await this.page.goto('https://www.nykaa.com/',{waitUntil:"domcontentloaded"})
    }
   
    async searchfor(itemname:string )
    {
       
        await this.page.getByRole("textbox",{name:"Search on Nykaa"}).fill(itemname)
        await this.page.getByRole("textbox",{name:"Search on Nykaa"}).press('Enter')
    }
}