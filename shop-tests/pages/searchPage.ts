import {Page,expect} from '@playwright/test'

export class SearchPage {
    
    constructor(private readonly page:Page){}
    
    private searchBox = () => this.page.getByRole('searchbox');
    private searchButton = () =>this.page.getByRole('button',{name: 'Search'});
    
    result = () => this.page.getByTestId('product-card');

    async goto(){
        await this.page.goto('/catalog');
    }
    
    async search(query: string){
        const res = this.page.waitForResponse( 
            (r)=>r.url().includes('/api/products') && r.status() === 200
        )
        await this.searchBox().fill(query);
        await this.searchButton().click();
        await res;
    }

    async isSearchcountEmpty(){
        await expect.soft(this.page.getByTestId('catalog-result-count')).not.toHaveText("Showing 0 products");
    }

    async equalSearchCount(message:string){
        await expect.soft(this.page.getByTestId('catalog-result-count')).toContainText(" ${message} ");
    }

    async selectFirstProduct(){
        await this.result().first().getByRole('link',{name: /view/i}).click();
    }

    async viewProductByName(productName:string){
        await this.result().getByRole('link',{name: productName}).click()
    }
    
}


