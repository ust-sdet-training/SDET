import { Page,expect } from "@playwright/test";
import { SearchPage } from "../pages/searchPage";
import { ProductPage } from "../pages/productPage";
import { CartPage } from "../pages/cartPage";
import { CheckoutPage } from "../pages/checkoutPage";
import { OrderPage } from "../pages/orderPage";

export class shopFlow{
    private readonly searchPage:SearchPage;
    private readonly productPage:ProductPage;
    private readonly cartPage:CartPage;
    private readonly checkoutPage:CheckoutPage;
    private readonly orderPage:OrderPage;

    constructor(private readonly page:Page){
        this.searchPage = new SearchPage(page);
        this.productPage = new ProductPage(page);
        this.cartPage = new CartPage(page);
        this.checkoutPage = new CheckoutPage(page);
        this.orderPage = new OrderPage(page);
    }

    async searchExistingProduct(query:string){
        await this.searchPage.goto();
        await this.searchPage.search(query);
        await this.searchPage.isSearchcountEmpty();
        await expect.soft(this.searchPage.result().getByRole('heading',{name: query})).toBeVisible();
    }

    async viewProductUsingSearch(productName:string){
        await this.searchPage.goto();
        await this.searchPage.search(productName);
        await this.searchPage.isSearchcountEmpty();
        await expect.soft(this.searchPage.result().getByRole('heading',{name: productName})).toBeVisible();
        await this.searchPage.viewProductByName(productName);
    }

    async viewFirstProduct(){
        await this.searchPage.selectFirstProduct();
    }

    async addFirstResultToCart(){
        await this.viewFirstProduct();
        await this.productPage.addToCart();
    }

    async addProducttoCartbyProductLink(productlink : string){
        await this.page.goto(`/product${productlink}`)
    }

    async cartCheckout(){
        await this.cartPage.goto();
        await this.cartPage.checkout();
    }

    async placeOrder(){
        await this.checkoutPage.goto()
        await this.checkoutPage.checkout();
    }

    async validateOrder(){
        await this.orderPage.goto();
        await this.orderPage.validateOrder();
    }
}