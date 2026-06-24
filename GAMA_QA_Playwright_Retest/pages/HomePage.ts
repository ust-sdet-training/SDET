import {Page} from '@playwright/test'

export class HomePage{
     constructor(private readonly page:Page){}
     
     private Search=()=>this.page.getByRole("textbox", {name: /search on nykaa/i});

     async goto(){
        await this.page.goto('https://www.nykaa.com', {waitUntil:'domcontentloaded'});
     }

     async searchProduct(productName:string){
        // await this.Search().click();
        await this.Search().fill(productName);
        // await this.Search().click();
        await this.Search().press('Enter');
     }
}