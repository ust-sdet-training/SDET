import {expect,Page} from "@playwright/test"
import {Flightpage} from '../pages/FlightPage'

export class Flightflows{

    readonly ff : Flightpage

    constructor(public readonly page:Page){

        this.ff = new Flightpage(page)

    }

    async search()
    {

        await expect(this.page).toHaveURL("/")
        await expect(this.page.getByRole('heading', {name:/Book flights/, level:1})).toBeVisible()
        await expect(this.page.getByRole('tab', {name:/Flights/}))
        await expect(this.page.getByRole('heading', {name:/Popular routes/, level:2})).toBeVisible()

        await this.ff.flight()
    }


}