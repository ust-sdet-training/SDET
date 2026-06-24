import {Page,Locator,expect} from '@playwright/test';

export class HomePage {

    constructor(readonly page:Page){}

    nykaaLogo = () : Locator => this.page.getByRole('link', { name: 'Nykaa Logo' });
    searchOnNykaaInput = () : Locator => this.page.getByRole('textbox', { name: 'Search on Nykaa' });

    async isHomePageDisplayed():Promise<boolean>{
        return await this.nykaaLogo().isVisible();
    }

    async searchProduct(productName: string){
        await this.searchOnNykaaInput().fill(productName);
        await this.searchOnNykaaInput().press('Enter');
    }

    async goToNykaa(){
        await this.page.goto('/');
    }



}