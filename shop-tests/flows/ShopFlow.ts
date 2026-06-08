import { expect, type Page } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { testUsers } from "../fixtures/test-users";
import { CatalogPage } from "../pages/CatalogPage";
import { ProductPage } from "../pages/ProductPage";


export class ShopFlow{
    readonly loginPage: LoginPage;
    readonly catalogPage: CatalogPage;
    readonly productPage: ProductPage;
    constructor(private readonly page: Page) {
        this.loginPage = new LoginPage(page);
        this.catalogPage = new CatalogPage(page);
        this.productPage = new ProductPage(page);
    }

    loginSuccessFlow = async () => {
        await this.loginPage.goto();
        await this.loginPage.login(testUsers.customer.email, testUsers.customer.password);

        await expect(this.page).toHaveURL(/\/home/);
        await expect.soft(this.page.getByRole('heading', {name: `Welcome, ${testUsers.customer.displayName}`})).toBeVisible();
        await expect.soft(this.page.getByRole('button', { name: 'Sign out' }));
    }

    loginFailureFlow = async () => {
        await this.loginPage.goto();
        await this.loginPage.login(testUsers.invalid.email, testUsers.invalid.password);

        await expect.soft(this.page).toHaveURL(/\/login/);
        await expect.soft(this.page.getByTestId('login-error')).toBeVisible();
    }

    searchQuery = async (q: string) => {
        await this.catalogPage.goto();
        await this.catalogPage.search(q);  
    }

    addProductToCart = async (productName: string) => {
        await this.productPage.goto(productName);
        await this.productPage.addToCart();
    }
}