import {expect,Page,Locator} from '@playwright/test';

export class HomePage {
    constructor(private page: Page) {}


    async navigateToHomePage():Promise<void> {
        await this.page.goto('/');
        await this.page.waitForURL('https://www.nykaa.com/');
    }

    async positiveSearch(item: string):Promise<void> {
        await this.navigateToHomePage();
        await expect(this.page.getByRole('textbox', { name: 'Search on Nykaa' })).toBeVisible();
       await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
       await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill(item);
       await this.page.waitForLoadState('load');
     this.page.locator('span').filter({ hasText: 'shirt for man' }).first().click();
    }

async negativeSearch(item: string):Promise<void>
 {
     await this.navigateToHomePage();
     await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('adbduidsjhksdfksfksdj');
     await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');

    }

}



