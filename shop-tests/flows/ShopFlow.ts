import { Page } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";

export class ShopFlow {

readonly login;
readonly search;
readonly product;
readonly cart;
readonly checkout;

constructor(page: Page){

    this.login =new LoginPage(page);
    this.search = new SearchPage(page);
    this.product = new ProductPage(page);
    this.cart = new CartPage(page);
    this.checkout =new CheckoutPage(page);
    }

async doLogin(){
    await this.login.login('customer@example.com','Password@123');
    await this.login.verifySuccess();
}

async loginLockedUser(){
    await this.login.login('locked@example.com','Password@123');
    await this.login.verifyFailure();
}

async searchProduct(){
    await this.search.open();
    await this.search.search('travel');
    await this.search.openProduct('Travel Backpack');
}

async searchNoResults(){
    await this.search.open();
    await this.search.searchNoResults('abcdwd');
}

async emptySearch(){
    await this.search.open();
    await this.search.emptySearch();
}

async configureProduct(size:string,color:string,quantity:string,fulfilment:string){
    await this.product.waitForPage();
    await this.product.configure(size,color,quantity,fulfilment);
}

async addCart(){
    await this.product.addToCart();
}

async checkoutOrder(){
    await this.cart.verify();
    await this.cart.checkout();
}

async placeOrder(){
    await this.checkout.fillCheckout();
    await this.checkout.placeOrder();
    await this.checkout.verifyOrder();
}

}

