import {Page,expect} from '@playwright/test'

export class HomePage{
     constructor(private readonly page:Page){}
     
     private Search=()=>this.page.getByPlaceholder('Search');

     async goto(){
        await this.page.goto('/');
     }

     async searchProduct(productName:string){
        const response = await this.page.waitForResponse(response => response.url().includes('/a.clarity.ms/collect') && response.status() === 204);
        await this.Search().fill(productName);
        await this.Search().click();
        await this.Search().press('Enter');
        await response;
       
     }

     
 }