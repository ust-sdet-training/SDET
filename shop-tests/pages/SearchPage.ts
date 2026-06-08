import {Page} from '@playwright/test';

export class SearchPage {
    constructor(private readonly page: Page) {}
        private box = () => this.page.getByRole('searchbox');
        results = () => this.page.locator('.product-grid');

        async search(q: string){
            await this.page.goto('/catalog');
            const res = this.page.waitForResponse(
                r => r.url().includes('/api/products') && r.status() === 200
            );
            await this.box().fill(q);
            await this.box().press('Enter');
            await res;
        }
    }

