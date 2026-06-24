import { expect, Page } from '@playwright/test';


import { SearchPage } from "../pages/SearchPage";

export class ShopFlow {
  constructor(private searchPage: SearchPage) {}

  async searchForWatch(page:Page) {
    await this.searchPage.goto();
    await this.searchPage.logo();
    await expect(page.getByRole('link', { name: 'Nykaa Logo' })).toBeVisible();
await expect(page.getByRole('link', { name: 'BEAUTY BONANZA Get Your' })).toBeVisible();
    await this.searchPage.searchBox();
    

    await this.searchPage.category();
    await this.searchPage.watchesCategory();
   
  }

  async searchInvalidProduct() {
    await this.searchPage.goto();
    await this.searchPage.logo();
    await this.searchPage.searchBox();
    await this.searchPage.invalidSearch();
    await this.searchPage.clickSearch();
    await expect(this.searchPage.text()).toBeVisible();
    await expect(this.searchPage.unfortunatelyText()).toBeVisible();
    await this.searchPage.backToHome();

  }

  async openBeautyBonanza() {
    await this.searchPage.goto();
    await this.searchPage.getBeautyBonanza();
  }


}
