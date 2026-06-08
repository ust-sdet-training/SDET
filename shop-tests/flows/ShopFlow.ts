import { expect, type Page } from '@playwright/test';
 
import { LoginPage } from '../pages/LoginPage';
import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';
import { CartPage } from '../pages/CartPage';
import { Checkout} from '../pages/Checkout';
 
export class ShopFlow {
 
    readonly loginPage: LoginPage;
    readonly searchPage: SearchPage;
    readonly productPage: ProductPage;
    readonly cartPage: CartPage;
    readonly checkout: Checkout;
 
    constructor(private readonly page: Page) {
 
        this.loginPage = new LoginPage(page);
        this.searchPage = new SearchPage(page);
        this.productPage = new ProductPage(page);
        this.cartPage = new CartPage(page);
        this.checkout = new Checkout(page);
    }
 
    //login page
    async Login(email: string, password: string) {
        const loginForm = this.page.getByRole("form", { name: "Login" });
        await loginForm.getByLabel("Email").fill(email);
        await loginForm.getByLabel("Password").fill(password);
        await expect(this.page.getByRole("button", { name: "Sign in" })).toBeEnabled();
        await loginForm.getByRole("button", { name: "Sign in" }).click();
 
        await expect(this.page).toHaveURL(/\/home$/);
        await expect(this.page.getByRole("button", { name: "Sign out" })).toBeVisible();
 
        // await this.loginPage.login(email,password);
 
    }
 
    // Searching the product
    async productSearch(productName: string) {
 
        await this.searchPage.search(productName);
 
    }
 
    //adding the product to the cart
    private async addToCart(productName: string) {
 
        await this.productPage.addProductToCart(productName);
 
    }
 
    //checkouts the order
    private async checkOut() {
        await this.cartPage.checkout();
        await this.checkout.order();
    }
 
    async buyProduct(email: string, password: string, productName: string) {
 
        await this.Login(email,password);
 
        await this.productSearch(productName);
 
        await this.addToCart(productName);
       
        await this.checkOut();
    }
}
 