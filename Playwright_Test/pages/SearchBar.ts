import { expect, test } from '@playwright/test';
import { Page } from '@playwright/test';

export class SearchBar {
    constructor(private readonly page: Page) {
    }

    async goto(){
        await this.page.goto('https://www.nykaa.com/');
    }

    async searchbarFound(){
        // await this.page.locator('.active.css-1nzkux7').click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
         await this.page.locator('#headerMenu').getByRole('button').click();
    }

    async searchbarProductFound(){
        // await this.page.locator('.active.css-1nzkux7').click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('Shampoo');
        await this.page.keyboard.press('Enter');
    }

    async searchbarUnknownProductFound(){
        // await this.page.locator('.active.css-1nzkux7').click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('xyzz');
        await this.page.keyboard.press('Enter');
    }

    async searchbarProductNotFound(){
        // await this.page.locator('.active.css-1nzkux7').click();
        await this.page.getByRole('textbox',{name: 'Search on Nykaa'}).click;
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('@#$');
        await this.page.keyboard.press('Enter');
    }


}
