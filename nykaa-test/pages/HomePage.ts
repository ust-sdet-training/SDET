import {type Page,expect} from '@playwright/test';

export class HomePage{
    constructor(private readonly page: Page) {}

    async home(){
    await this.page.goto('https://www.nykaa.com/');
    await expect.soft(this.page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible()
    await expect.soft(this.page.getByRole('link', { name: 'logo', exact: true })).toBeVisible();
    await this.page.getByRole('textbox', { name: 'Search' }).click();
    await this.page.getByRole('textbox', { name: 'Search' }).fill('shoes');
    await this.page.getByRole('textbox', { name: 'Search' }).press('Enter');
    await expect.soft(this.page.getByRole('heading', { name: 'Shoes' })).toBeVisible();
    }



}