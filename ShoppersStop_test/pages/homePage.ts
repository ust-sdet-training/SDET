import { expect, Page } from '@playwright/test';

export class HomePage {

    constructor(private page: Page) {}

    private searchBox = this.page.getByRole('textbox', { name: 'Search' });

    async navigate() {
        await this.page.goto('https://www.shoppersstop.com/');
        await this.page.waitForLoadState('domcontentloaded');
    }

    async verifyTitle() {
        await expect(this.page).toHaveTitle(/Shoppers Stop/i);
    }

    async searchProduct(product: string) {

        await expect(this.searchBox).toBeVisible();
        await this.searchBox.fill(product);
        await this.page.getByText(/^Watches$/).first().click();
    }

}