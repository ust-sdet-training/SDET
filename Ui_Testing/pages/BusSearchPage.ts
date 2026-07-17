import {Page} from '@playwright/test'
export class BusSearchPage
{
    constructor (public readonly page:Page)
    {

    }
    async openBusPage()
    {
        await this.page.getByRole("tab",{name:"Buses"}).click();
    }
    async fillTripDetails(fromAddress:string,toAddress:string,date:string)
    {
        await this.page.getByRole("combobox",{name:"From"}).click()
        await this.page.getByRole('combobox', { name: 'From' }).fill(fromAddress);
        await this.page.getByRole("option").first().click()

        
        await this.page.getByRole("combobox",{name:"To"}).click()
        await this.page.getByRole('combobox', { name: 'To' }).fill(toAddress);
        await this.page.getByRole("option").first().click()



        await this.page.getByRole('textbox', { name: 'Date' }).fill(date);
        await this.page.getByRole('button', { name: 'Search' }).click();

    }
}