import {expect,Page} from "@playwright/test"
import {Seatspage} from '../pages/SeatsPage'

export class Seatflows{

    readonly sp : Seatspage

    constructor(public readonly page:Page){

        this.sp = new Seatspage(page)

    }

    async seat()
    {

        await expect(this.page).toHaveURL(/FL-DELBLR-51/)
        await expect(this.page.getByText('Vistara')).toBeVisible()

        await this.sp.seatSelect()
    }


}