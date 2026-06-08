import {type Page,expect} from '@playwright/test';

export class SearchPage{
    constructor(private readonly page: Page) {}

    async search(){
    this.page.goto('/catalog');
    await expect(this.page.getByRole('heading',{name:'Product Catalog'})).toBeVisible();
    await expect(this.page.getByLabel("Search products")).toBeVisible();
    await this.page.getByLabel('Search products').fill("Running Shoes");
    await this.page.getByRole('button',{name:"Search"}).click();
    await this.page.getByRole('link',{name:"View Running Shoes"}).click();
    await this.page.goto('/product/running-shoes');
    await this.page.getByRole('button',{name:"Add to cart"}).click();
    }



}