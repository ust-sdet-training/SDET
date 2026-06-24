import {Page,expect} from '@playwright/test'

export class HomePage{
     constructor(private readonly page:Page){}
     
     private Search=()=>this.page.getByPlaceholder('Search');

     async goto(){
        await this.page.goto('https://www.shoppersstop.com/');
        await this.page.waitForURL(/shoppersstop\.com/);
     }

     async searchProduct(productName:string){
        const response = this.page.waitForResponse(response => response.url().includes('/collect') && response.status() === 204);
         await this.Search().fill(productName);
        await this.Search().click();
        await this.Search().press('Enter');
        await response;
       
     }

     
 }