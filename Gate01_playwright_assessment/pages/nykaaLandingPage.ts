import {expect, Page} from '@playwright/test';
export class nykaaLandingPage
{
    constructor(private readonly page:Page)
    {}
    async search_product(itemName:string)
    {
        await this.page.getByPlaceholder("Search on Nykaa").fill(itemName);
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
    }
    async viewTheProduct()
    {
         await this.page.locator('.css-5u3fim').click();
    }

}