import { Page, expect } from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';
import { CartPage } from '../pages/CartPage';
import { CheckoutPage } from '../pages/CheckoutPage';
import { OrderPage } from '../pages/OrderPage';

export class ShopFlow {
    private readonly searchPage: SearchPage;
    private readonly productPage: ProductPage;
    private readonly cartPage: CartPage;
    private readonly checkoutPage: CheckoutPage;
    private readonly orderPage: OrderPage;
    
    constructor(private readonly page: Page) {
        this.searchPage = new SearchPage(page);
        this.productPage = new ProductPage(page);
        this.cartPage = new CartPage(page);
        this.checkoutPage = new CheckoutPage(page);
        this.orderPage = new OrderPage(page);
    }

    async searchExistingProduct(query: string) {
        await this.searchPage.goto();
        await this.searchPage.search(query);
        await this.searchPage.isSearchCountEmpty();
        await expect(this.searchPage.results().getByRole("heading", {name: query})).toBeVisible();
    }

    async viewProductUsingSearch(productName: string) {
        await this.searchPage.goto();
        await this.searchPage.search(productName);
        await this.searchPage.isSearchCountEmpty();
        await this.searchPage.viewProductByName(productName);
    }

    async viewFirstProduct() {
        await this.searchPage.selectFirstProduct();
    }

    async addToProductToCartWithLink(productLink: string) {
        await this.page.goto(`/product/${productLink}`);
        await this.productPage.addToCart();
    }

    async addToProductToCart() {
        await this.viewFirstProduct();
        await this.productPage.addToCart();
    }

    async goToCheckout() {
        await this.cartPage.goto();
        await this.cartPage.proceedToCheckout();
    }

    async placeProductOrder() {
        await this.checkoutPage.goto();
        await this.checkoutPage.placeOrder();
    }

    async validateOrder() {
        await this.orderPage.goto();
        await this.orderPage.validateOrder();
    }
}