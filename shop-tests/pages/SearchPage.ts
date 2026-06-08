import { expect, type Page } from '@playwright/test';

export class SearchPage {
    constructor(private page: Page) {}
    private box = () => this.page.getByRole('searchbox');
    private searchButton = () => this.page.getByRole('button',{name:'Search'});
    results = () => this.page.getByTestId('catalog-result-count');
    
    async search(q: string) {
        const res = this.page.waitForResponse(
            r => r.url().includes('?search') && r.status() === 200
        );
        await this.box().fill(q);
        await this.box().press('Enter');
        await res;
    }

    async searchNoResults(query:string){
        await this.box().fill(query);
        await expect(this.box()).toHaveValue(query);
        await Promise.all([
            this.page.waitForResponse(r =>r.url().includes('/products') && r.status()===200),
            this.searchButton().click()
        ]);
        await expect(this.results()).not.toContainText(query);
    }
}