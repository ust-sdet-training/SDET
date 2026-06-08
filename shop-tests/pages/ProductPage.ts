import {expect,Locator,Page, test} from '@playwright/test'
 
export class ProductPage{
    constructor(private readonly page:Page){}
 
    Click_button =():Locator => this.page.getByRole('button',{name:"Add to cart"});
 
    async addToCart(){
         await this.Click_button().click();
    }
}