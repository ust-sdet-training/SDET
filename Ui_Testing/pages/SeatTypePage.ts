import {Page,Locator} from '@playwright/test'
export class SeatTypePage
{
    constructor (public readonly page:Page)
    {

    }
    async busType()
    {
        await this.page.getByText('A/C Sleeper', { exact: true }).click();

    }
    async selectSeat()
        {
                  await this.page.getByLabel('Kallada Travels').getByRole('button', { name: 'Select Seats' }).click();
        }

     public busname(): Locator 
    {
        return this.page.getByRole('heading', { name: 'Select seats — Kallada Travels' })
    }

    public seatavailable():Locator
    {
          return this.page.getByRole('button', { name: 'Seat L3 available' });
    }

    public seatavailablecheck():Locator
    {
                    return this.page.locator('[data-state="available"]').first();

    }

    async selectAvailableSeat()
    {
        //   await this.page.getByRole('tabpanel', { name: 'lower deck' }).;
             //await this.page.getByRole('button', { name: 'Seat L5 available' }).click();
             const availableSeat = this.page.locator('[data-state="available"]').first();
             await availableSeat.click();

    }

    async proceedToPassengerDeatils()
    {
          await this.page.getByRole('button', { name: 'Continue to passenger details' }).click();

    }
    

   
}