import {expect, type Page} from "@playwright/test";

export class SearchPage {
    constructor(private readonly page: Page) {}

    private box = () => this.page.getByRole('searchbox');
    results = () => this.page.getByTestId('.product-grid');

    async search(q: string){
        const response = this.page.waitForResponse(response => 
            response.url().includes('/api/products') && response.status() === 200);

    await this.box().fill(q);
    await response;
    await this.page.getByRole('button', {name : 'Search'}).click();
    await this.box().press('Enter');
    
    await this.results;
    }


}
