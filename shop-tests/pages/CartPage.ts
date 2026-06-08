import {expect, type Page} from "@playwright/test";
import { SearchPage } from "./SearchPage";

export class CartPage{
    constructor(private readonly page:Page){}

    async cartToCheckout(){

       const cartCheckout = new SearchPage(this.page);
           await cartCheckout.searchToCart();
           await this.page.goto("/cart");
           await this.page.getByRole("button",{name:"Proceed to checkout"}).click();
    }

}