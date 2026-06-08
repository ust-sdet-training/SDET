import { expect, Page } from "@playwright/test";

export class CatalogPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/catalog');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async search(query: string): Promise<void> {
        const res = this.page.waitForResponse(
            r => r.url().includes('search') &&
                    r.status() === 200);
        
        await this.page.getByLabel('Search products').fill(query);
        await this.page.getByRole('button', { name: 'Search' }).click();
        await res;
    }
}