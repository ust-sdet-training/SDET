import {expect,Locator,Page, test} from '@playwright/test'
  
export class ProductPage{
    constructor(private readonly page:Page){}
  
       


    Click_button =():Locator => this.page.getByRole('button',{name:"Add to cart"});

    quantity =(): Locator => this.page.getByLabel('Quantity');

    async addtocart(){
         await this.Click_button().click();
    }
   
    async invalid_quantity(){
        await this.quantity().click();
    }
}