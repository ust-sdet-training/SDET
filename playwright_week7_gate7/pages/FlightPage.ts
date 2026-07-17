import {Page} from '@playwright/test'

export class Flightpage
{
    constructor(public readonly page:Page){}

   

    async flight()
    {
        await this.page.getByRole('link',{name:'Flights'}).click()
    }   
}
 