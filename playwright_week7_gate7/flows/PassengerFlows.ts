import {expect,Page} from "@playwright/test"
import {Passengerpage} from '../pages/PassengerPage'

export class Passengerflows{

    readonly pp : Passengerpage

    constructor(public readonly page:Page){

        this.pp = new Passengerpage(page)

    }

    async passengerselect(first:string,last:string,age:string,gender:string,email:string,ph:string)
    {
        
        await expect(this.page).toHaveURL(/book/)
        await expect(this.page.getByRole('heading', {name:/travelling/, level:1})).toBeVisible()

        await this.pp.passenger(first,last,age,gender,email,ph)
    }


}