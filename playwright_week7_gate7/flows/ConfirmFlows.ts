import {expect,Page} from "@playwright/test"
import {Confirmpage} from '../pages/ConfirmPage'

export class Confirmflows{

    readonly conp : Confirmpage

    constructor(public readonly page:Page){

        this.conp = new Confirmpage(page)

    }

    async confirm()
    {

        await expect(this.page).toHaveURL(/confirmation/)
        await expect(this.page.getByRole('heading', {name:/all set/, level:1})).toBeVisible()
        await expect(this.page.locator('[data-id="state"]')).toHaveText("CONFIRMED")
       

        await this.conp.confirmation()
    }


}