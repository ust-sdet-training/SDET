import{Page, expect} from "@playwright/test"
import{NykaaPage} from "../pages/NykaaPage"
 
export class NykaaFlow{

    readonly pg : NykaaPage
    constructor(public readonly page:Page)
    {
        this.pg = new NykaaPage(page)
    }


    async HomeFlow(query:string)
    {
        this.pg.goto()
        await expect(this.page).toHaveURL("/?eq=desktop")
        await expect(this.page.getByRole("link", {name:"Nykaa Logo"})).toBeVisible()
        await expect(this.page.getByRole("textbox", {name:"Search on Nykaa"})).toBeVisible()
        await expect(this.page.getByRole("button", {name:"Sign in"})).toBeVisible()

        this.pg.HomePage(query)
        await expect(this.page).toHaveURL(/watches/)
        await this.page.getByText("/Showing 20")
    }


    
    async NegativeHomeFlow(query:string)
    {
        this.pg.goto()
        await expect(this.page).toHaveURL("/?eq=desktop")
        await expect(this.page.getByRole("link", {name:"Nykaa Logo"})).toBeVisible()
        await expect(this.page.getByRole("textbox", {name:"Search on Nykaa"})).toBeVisible()
        await expect(this.page.getByRole("button", {name:"Sign in"})).toBeVisible()

        this.pg.HomePage(query)
        await expect(this.page).toHaveURL(/abcdejh/)
       
    }


}