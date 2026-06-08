import type {Page} from  '@playwright/test';
export class SearchPage {
    constructor(private readonly page : Page){}
    private box = () => this.page.getByRole('searchbox', { name: 'Search products' });
    results = () => this.page.getByTestId('result');
    async search(q : string){ 
        const res = this.page.waitForResponse( r => r.status()===200);
        await this.box().fill(q);
        await this.page.getByRole('button',{name :'Search'}).click();
        await res;
    }

}