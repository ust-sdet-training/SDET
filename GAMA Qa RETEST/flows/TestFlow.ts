import {expect,Page} from "@playwright/test"
import {TestPage} from "../pages/TestPage"
export class TestFlow{

    private readonly testpage:TestPage;
    constructor (private readonly page:Page)
    {
        this.testpage=new TestPage(page)

    }
    async ptest(name:string)
    {
        await this.testpage.goto()
        await expect(this.page.getByRole("img",{name:"Nykaa logo"})).toBeVisible
        await this.testpage.searchfor(name)
        await expect(this.page.getByRole("heading",{name:"Buy Shampoos Online"})).toBeVisible
        await expect(this.page.getByRole('listitem').filter({ hasText: /^Shampoo$/ })).toBeVisible

    }
    async ntest(name:string)
    {
        await this.testpage.goto()
        await expect(this.page.getByRole("img",{name:"Nykaa logo"})).toBeVisible
        await this.testpage.searchfor(name)
        await expect(this.page.getByRole("img",{name:"no_result"})).toBeVisible
        await expect(this.page.getByRole("button",{name:"Back to Home"})).toBeVisible


    }

}