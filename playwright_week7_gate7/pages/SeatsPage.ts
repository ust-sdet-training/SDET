import {Page,expect} from '@playwright/test'

export class Seatspage
{
    constructor(public readonly page:Page){}

   

    async seatSelect()
    {
        const seat = this.page.locator('.seat.available').first()
        await seat.scrollIntoViewIfNeeded()
        await expect(seat).toBeVisible();
        await seat.click();
        const btn=await this.page.locator('#continue-btn')
        await expect(btn).toBeEnabled({ timeout: 10000 })
        await btn.click();

    }   
}
 