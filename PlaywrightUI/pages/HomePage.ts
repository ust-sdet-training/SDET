import {Page,expect} from '@playwright/test'

export class HomePage{
     constructor(private readonly page:Page){}
     
     private Search=()=>this.page.getByPlaceholder('Search on Nykaa');
     private Title=()=>this.page.getByRole('link',{name:"Nykaa Logo"});

     async goto(){
        await this.page.goto('/',{waitUntil:'domcontentloaded'});
        await this.page.waitForURL(/www\.nykaa\.com/);
        
     }

     async searchProduct(productName:string){
        const response = this.page.waitForResponse(response => response.url().includes('/collect') && response.status() === 204);
        await expect(this.Title()).toBeVisible();
        await this.Search().fill(productName);
        await this.Search().click();
        await this.Search().press('Enter');
        await response;
       
     }

     
 }