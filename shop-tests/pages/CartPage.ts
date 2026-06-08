// import { Page,Locator } from "@playwright/test"
// export class CartPage{
//     // readonly cartcount:Locator;
//     constructor(private readonly page:Page){
//         private cartcount=()=> this.page.getByTestId('cart-count');
    
//     async addtocart():Promise<void>{
//         const addpromise=this.page.waitForResponse(res=>res.url().includes('/cart') && res.status()===200);
//         await this.page.getByTestId
//     }
// }
// export class CartPage{
//     constructor(private readonly page:Page){}
//     async open(){
//         await this.page.goto('/product/running-shoes');
//     }
//     async addcart(){
//         const resp=this.page.waitForResponse(res=> res.url().includes('/api/cart') && res.status()===200);
//         await this.page.getByRole('button',{name:'Add to cart'}).click();
//         await resp;
//     }
//     cartcount=()=> this.page.getByRole('heading',{name:'Cart'});
//     prodcount=()=> this.page.getByTestId('cart-count');
// }
// import { Page, Locator } from "@playwright/test";
// export class CartPage {
//     readonly cartCount: Locator;
//     constructor(private readonly page: Page) {
//         this.cartCount =page.locator("text=/Cart count:/");}
//     async open(): Promise<void> {
//         await this.page.getByRole("link", {name: "Cart"}).click();
//   }
// }

import { Page, Locator } from "@playwright/test";
export class CartPage {
    readonly cartCount: Locator;
    readonly checkoutButton: Locator;
    constructor(private readonly page: Page) {
        this.cartCount =page.locator("text=/Cart count:/");
        this.checkoutButton =page.getByRole("button", {name: /proceed to checkout/i});
  }

async open(): Promise<void> {
    await this.page.getByRole("link", {name: "Cart"}).click();
  }

async proceedToCheckout(): Promise<void> {
    await this.checkoutButton.click();
  }
}