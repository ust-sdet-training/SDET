import {Page} from '@playwright/test'

export class Confirmpage
{
    constructor(public readonly page:Page){}


    async confirmation()
    {

        await this.page.getByRole('button',{name:/View/}).click()

    }   
}
