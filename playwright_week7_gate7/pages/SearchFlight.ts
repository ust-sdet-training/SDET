import {Page} from '@playwright/test'

export class Searchpage
{
    constructor(public readonly page:Page){}

   

    async search(from:string, to:string, cabin:string, date:string)
    {
        await this.page.getByRole('combobox',{name:'From'}).fill(from)
        await this.page.getByRole('combobox',{name:'To'}).fill(to)

        await this.page.getByLabel('Cabin class').selectOption(cabin)
        await this.page.getByRole('button', { name: date }).click();  
        await this.page.getByRole('button', { name: 'Search' }).click();

    }   
}
 