import {type Page,expect} from '@playwright/test';

export class SearchPage{
    constructor(private readonly page: Page) {}

    async search(){
    await this.page.goto('https://www.nykaa.com/');
    await expect(this.page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
    await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
    await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('shoes');
    await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
    }

    async searchNotProduct(){
        await this.page.goto('https://www.nykaa.com/');
        await this.page.getByRole('textbox', { name: 'Search' }).click();
        await this.page.getByRole('textbox', { name: 'Search' }).fill('kavya');
        await this.page.getByRole('textbox', { name: 'Search' }).press('Enter');
        await this.page.getByText('No Result Found');
        await this.page.getByText('Looks like we couldn\'t find a');
    }



}