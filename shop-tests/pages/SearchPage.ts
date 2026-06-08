import {expect, Page, Locator } from "@playwright/test";

export class SearchPage {
  constructor(private readonly page: Page) {}

   resultBox = (): Locator => this.page.locator('#search-products');
   resultCount =():Locator => this.page.getByTestId('catalog-result-count');

  async open():Promise<void> {
    await this.page.goto("/catalog");
  }


   async search(searchKey:string):Promise<void>
   {
    await this.page.getByLabel('Search products').fill(searchKey);
    const searchPromise = this.page.waitForResponse(res => res.url().includes('search') && res.status()===200)
    await this.page.getByRole('button',{name:'Search'}).click();
    await searchPromise;
   }
}
