import { Page } from "@playwright/test";
 
export class ProductPage{
    constructor(private readonly page:Page){}
    
    async goto(item: string){
        await this.page.goto(`/product/${item}`);
    }

    async addCart(){
        const response = this.page.waitForResponse(resp =>resp.url().includes('/api/cart') && resp.status() === 200);
        await this.page.getByRole("button",{name: "Add to cart"}).click();
        await response;
    }


    cartCount=()=> this.page.getByRole('heading',{name:'Cart'});
    count=()=>this.page.getByTestId('cart-count');
    outOfStock=()=>this.page.getByText('Unable to add this item. Check stock and try again.');


}