import { expect, Page } from "@playwright/test";

export class Search {

    constructor(private readonly page: Page) {}

 searchInput = () => this.page.getByPlaceholder('Search name');
 result =()=> this.page.getByLabel('Product results');

 noProductsMessage = () => this.page.getByText('No products found');
    async goto() {
        await this.page.goto('/catalog');
    }
    async searchProduct(product: string) {    
        await this.searchInput().fill(product);
        const responsePromise =
        this.page.waitForResponse(
            response =>
                response.url().includes('/api/products') &&
                response.status() === 200
        );
         await this.page
        .getByRole('button', {
            name: 'Search'
        })
        .click();
         await responsePromise;
    }

    //

    async openProduct(product: string) {
        await this.page
        .getByRole('link', {
            name: product
        })
        .click();
    }
}