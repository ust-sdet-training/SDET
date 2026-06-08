import { Page ,Locator } from '@playwright/test';

export class CatalogPage {

    constructor(private readonly page: Page){}

    searchInput = () : Locator => this.page.getByLabel('Search products');
    searchButton = () : Locator => this.page.getByRole('button',{name:'Search'});
    catalogTitleHeading = () : Locator => this.page.getByRole('heading',{level:1});
    viewProductButton =(productName: string) : Locator => this.page.getByRole('link',{name:`View ${productName}`});
    productCount = () : Locator => this.page.getByTestId('catalog-result-count');


    async searchProduct(productName: string){
        await this.searchInput().fill(productName);
        await this.searchButton().click();
    }

    async isCatalogTitleHeadingVisible(): Promise<boolean>{
       return await this.catalogTitleHeading().isVisible();
    }

    async viewProduct(productName: string){
        await this.viewProductButton(productName).click();
    }

    async getProductCountText():Promise<string>{
        return await this.productCount().textContent() || '';
    }

    async clickOnSearchButton(){
        await this.searchButton().click();
    }

    

}