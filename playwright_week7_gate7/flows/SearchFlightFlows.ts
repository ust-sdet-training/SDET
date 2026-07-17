import {expect,Page} from "@playwright/test"
import {Searchpage} from '../pages/SearchFlight'

export class Searchflows{

    readonly ff : Searchpage

    constructor(public readonly page:Page){

        this.ff = new Searchpage(page)

    }

    async search(from:string, to:string, cabin:string, date:string)
    {
        
        await expect(this.page).toHaveURL("/flights/search")
        await expect(this.page.getByRole('heading', {name:/Search flights/, level:1})).toBeVisible()
        await expect(this.page.getByRole('tab', {name:/Flights/}))

        await this.ff.search(from, to, cabin,date)
    }


}