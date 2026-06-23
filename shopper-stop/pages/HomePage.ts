import {type Page,expect} from '@playwright/test';

export class HomePage{
    constructor(private readonly page: Page) {}

   async goto(){
    await this.page.goto("/login");
   }

    async check(){

    await this.page.goto('http://www.shopperstop.com/');
    await expect(this.page).toHaveURL(/\/$/);
    await expect(this.page).toHaveTitle(/STOPPERS STOP/);
  }


}