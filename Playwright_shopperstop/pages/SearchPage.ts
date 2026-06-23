import { Locator, Page } from '@playwright/test';
export class SearchPage{
    constructor(private page:Page){}

   

    async searchproduct(product:string){
        const searchbox=this.page.getByRole('textbox',{name: "Search"});
        await searchbox.click();
        await searchbox.fill(product);
        await this.page.keyboard.press('Enter');
    }
    async openfirstproduct(){
        await this.page.getByTestId("product-card").first().click();
    }
}
