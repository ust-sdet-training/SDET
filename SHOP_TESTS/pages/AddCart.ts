import { Page } from "@playwright/test";
import { Search } from "./Search";

export class AddCart{
 constructor(private readonly page:Page){}

 async goto(){
    await this.page.goto('/product/running-shoes');
 }
 async addCart(){
     const responsePromise =
        this.page.waitForResponse(
            response =>
                response.url().includes('/api/cart') &&
                response.status() === 200
        );
    await this.page.getByRole('button',{name:'Add to cart'}).click();
    await responsePromise;

 }
 cartCount=()=> this.page.getByRole('heading',{name:'Cart'});
 count=()=>this.page.getByTestId('cart-count');
stockerror=()=>this.page.getByText('Unable to add this item. Check stock and try again.')
}