import { Page ,Locator } from '@playwright/test';

export class HomePage {

    constructor(private readonly page: Page){

    }

    searchInput = () : Locator => this.page.getByRole('textbox', { name: 'Search' });
    searchSuggestion = (suggestion : string) : Locator => this.page.getByText(suggestion, { exact: true }).nth(1);


    async gotoShoppersStop(){
        await this.page.goto('https://www.shoppersstop.com/')
    }
   

    async searchProduct(productName: string){
        await this.searchInput().click();
        await this.searchInput().fill(productName);
        await this.searchSuggestion(productName).click();
    }



    

    

}