import {expect,Page} from "@playwright/test"
import {Bookpage} from '../pages/BookPage'

export class Bookflows{

    readonly bp : Bookpage

    constructor(public readonly page:Page){

        this.bp = new Bookpage(page)

    }

    async book(email:string,phone:string)
    {
        
        await expect(this.page).toHaveURL(/payment/)
        await expect(this.page.getByRole('heading', {name:/Secure checkout/, level:1})).toBeVisible()

        await this.bp.bookit(email,phone)
    }


}