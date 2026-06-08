import { Locator, Page } from '@playwright/test';

export class ProductsPage {
    readonly page: Page;
    readonly searchBox: Locator;

    constructor(page: Page) {
        this.page = page;

        this.searchBox = page.getByRole('searchbox');
    }

    async searchProduct(
        productName: string
    ): Promise<void> {

        await this.searchBox.fill(productName);
    }

    results = () => this.page.getByTestId('catalog-result-count');

    async openProduct(
        productName: string
    ): Promise<void> {

        await this.page
            .getByRole('link', {
                name: `View ${productName}`
            })
            .click();
    }

    productCards(): Locator {
    return this.page.locator(
        '.product-card'
    );
}
}