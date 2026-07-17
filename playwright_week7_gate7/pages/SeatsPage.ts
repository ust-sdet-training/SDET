import {Page} from '@playwright/test'

export class Seatspage
{
    constructor(public readonly page:Page){}

   

    async seatSelect(seatnumber:string)
    {
        const seat = this.page.locator('.seat.available').first();
        await seat.click();
        await this.page.getByRole('button',{name:/Continue/}).click()
    }   
}
 