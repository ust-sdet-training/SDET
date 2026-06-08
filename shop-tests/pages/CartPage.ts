import { Page } from "@playwright/test";
 
export class CartPage{
 constructor(private readonly page:Page){}
 
 async goto(){
    await this.page.goto('/cart');
 }
 async checkout(){
      const responsePromise =
        this.page.waitForResponse(response =>response.url().includes('/api/cart') &&response.status() === 200);
        // await (this.page.getByRole('button',{name:'Proceed to checkout'})).click();
        await this.page.getByRole('button', {name: /checkout/i}).click();
   
        await responsePromise;
 
 }
 async placeOrder(){
    await this.page.getByRole('button',{name:'Place order'}).click();
 }
 
}