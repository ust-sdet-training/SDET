import {Page} from '@playwright/test'

export class Resultpage
{
    constructor(public readonly page:Page){}

   

    async flightname()
    {
        await this.page.getByLabel('Vistara UK-483').getByRole('button', { name: 'Book' }).click();
    }   
}
 