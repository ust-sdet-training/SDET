import {expect,type Page} from "@playwright/test"

export class SearchPage{

    constructor(private page:Page){}

    async goto(){
        await this.page.goto("https://tripstack.doomple.com/");
    }

    async search(){
        await this.page.getByRole('tab', { name: 'Buses' }).click();
        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('option', { name: 'Delhi DEL' }).click();
        await this.page.getByRole('combobox', { name: 'To' }).click();
        await expect(this.page.getByRole('combobox', { name: 'To' })).toBeEmpty();
        await this.page.getByRole('combobox', { name: 'To' }).click();
  await this.page.getByRole('option', { name: 'Chandigarh IXC' }).click();
  await this.page.getByRole('button', { name: 'Search' }).click();
        
    }

    async errorMessage(){
        return this.page.locator("#error");
    }
}
