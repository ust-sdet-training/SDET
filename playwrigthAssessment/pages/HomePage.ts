import {expect,Page,Locator} from '@playwright/test';

export class HomePage {
    constructor(private page: Page) {}

    searchBox = (): Locator => this.page.getByRole('textbox', { name: 'Search' });

    productCard =(): Locator => this.page.getByRole('link', { name: 'product card Casio Vintage' });
   noResultText =(): Locator => this.page.getByText('No Result Found');
   

    async navigateToHomePage():Promise<void> {
        await this.page.goto('https://www.shoppersstop.com/', { waitUntil: 'domcontentloaded' ,timeout: 60000});
    }

    async searchForItem(item: string):Promise<void> {
        await this.searchBox().click();
        await this.searchBox().fill(item);
        await this.searchBox().press('Enter');
    }


    async verifyLogoAndHomePageVisble():Promise<void> {
        await expect(this.searchBox()).toBeVisible();
    }

}

