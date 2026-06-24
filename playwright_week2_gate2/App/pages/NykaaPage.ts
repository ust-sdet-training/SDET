import{Page, expect} from "@playwright/test"

export class NykaaPage{

    constructor(public readonly page:Page)
    {}

    async goto()
    {
      await  this.page.goto("/?eq=desktop",{waitUntil: "domcontentloaded"})  
   
    }


    async HomePage(query:string)
    {
    
        await this.page.getByRole("textbox", {name:"Search on Nykaa"}).fill(query)
        await this.page.getByRole("textbox", {name:"Search on Nykaa"}).press("Enter")
    }


}