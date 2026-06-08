import {expect,Page,Locator} from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';
import { CartPage } from '../pages/CartPage';
import { LoginPage } from '../pages/LoginPage';

export class ShopFlow
{
    readonly searchPage:SearchPage;
    readonly productPage:ProductPage;
    readonly cartPage:CartPage;
    readonly loginPage:LoginPage;

    constructor(private readonly page: Page) 
    {
        this.searchPage= new SearchPage(page);
        this.productPage = new ProductPage(page);
        this.cartPage = new CartPage(page);
        this.loginPage = new LoginPage(page);;
    }

    //login success,failed,locked user
    loginSuccess = async (email:string,password:string)=>{
        await this.loginPage.open();

        const loginForm = this.page.getByRole("form", { name: "Login" });
        await expect(loginForm.getByLabel("Email")).toBeVisible();
        await expect(loginForm.getByLabel("Password")).toBeVisible();
        await expect(loginForm.getByLabel("Country")).toBeVisible();
        await expect(loginForm.getByLabel("Remember me")).toBeVisible();


        await expect(loginForm.getByRole("button", { name: "Sign in" })).toBeVisible();
        await this.loginPage.login(email,password);
    }

     loginFailed = async (email:string,password:string)=>{
        await this.loginPage.open();

        const loginForm = this.page.getByRole("form", { name: "Login" });
        await expect(loginForm.getByLabel("Email")).toBeVisible();
        await expect(loginForm.getByLabel("Password")).toBeVisible();
        await expect(loginForm.getByLabel("Country")).toBeVisible();
        await expect(loginForm.getByLabel("Remember me")).toBeVisible();
        await expect(loginForm.getByRole("button", { name: "Sign in" })).toBeVisible();

        await this.loginPage.login(email,password);
    }

      lockeuser = async (email:string,password:string)=>{
        await this.loginPage.open();

        const loginForm = this.page.getByRole("form", { name: "Login" });
        await expect(loginForm.getByLabel("Email")).toBeVisible();
        await expect(loginForm.getByLabel("Password")).toBeVisible();
        await expect(loginForm.getByLabel("Country")).toBeVisible();
        await expect(loginForm.getByLabel("Remember me")).toBeVisible();
        await expect(loginForm.getByRole("button", { name: "Sign in" })).toBeVisible();
        
        await this.loginPage.login(email,password);
    }

    //search product

    searchProductSuccess = async(searchProduct:string)=>{
        await this.searchPage.open();
        await this.searchPage.search(searchProduct);
        await expect.soft(this.searchPage.resultCount()).toHaveText('Showing 1 product');
    }

    searchProductFailed = async(searchProduct:string)=>{
        await this.searchPage.open();
        await this.searchPage.search(searchProduct);
        await expect.soft(this.searchPage.resultCount()).toHaveText('Showing 0 products');
    }

    //add product

     addProductSuccess = async(searchProduct:string)=>
     {
        await  this.productPage.open();
        await this.productPage.addCart(searchProduct);
        await expect.soft(this.productPage.results()).toHaveText('1');
     }
    
    cartProductQuantity = async(searchProduct:string)=>
    {
        await this.cartPage.open();
        await this.productPage.open();
        await this.productPage.addCart(searchProduct);
        await expect.soft(this.cartPage.countProduct()).toBeVisible();
        await expect.soft(this.cartPage.totalCartPrice()).toBeVisible();
   }

   checkoutAndValidate =async(product:string):Promise<void>=>
   {

        this.addProductSuccess(product);

        this.page.once('dialog',async dialog =>{
            await dialog.dismiss();
        });
        await this.page.getByRole('button',{name:`Remove ${product}`}).click();
        await expect(this.page.getByTestId('cart-count')).toHaveText('1');

        await this.page.getByRole('button',{name:'Proceed to checkout'}).click();
        await expect.soft(this.page).toHaveURL(/\/checkout/);


        await this.page.getByRole('button',{name:'Place order'}).click();
        await expect.soft(this.page.getByRole('heading',{name:'Thank you for your order'})).toBeVisible();

        await this.page.getByRole('button',{name:'View order'}).click();
        await expect.soft(this.page).toHaveURL(/\/orders/);

   }
}
