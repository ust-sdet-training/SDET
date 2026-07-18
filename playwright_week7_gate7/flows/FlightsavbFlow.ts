import {expect,Page} from "@playwright/test"
import {Resultpage} from '../pages/resultpage'

export class Flightavb{

    readonly rp:Resultpage

    constructor(public readonly page:Page){

        this.rp= new Resultpage(page)

    }

    async available()
    {

        await expect(this.page).toHaveURL("/flights/results")
        await expect(this.page.getByRole('heading', {name:/Flights: DEL/, level:1})).toBeVisible()
        await expect(this.page.locator('#result-live-count')).toHaveText('48')
        await expect(this.page.locator('.op-name').filter({hasText:"Vistara"}).first()).toBeVisible()
        await expect(this.page.locator('.op-name').filter({hasText:/IndiGo/}).first()).toBeVisible()
        await expect(this.page.locator('.op-name').filter({hasText:/SpiceJet/}).first()).toBeVisible()

        await this.rp.flightname()
        
    }


}



  