import { expect, type Page } from '@playwright/test'
import { SearchPage } from '../pages/SearchPage'
import { ProductPage } from '../pages/ProductPage';
import { CartPage } from '../pages/CartPage';
export class ShopFlow {
    readonly searchPage
    readonly productPage
    readonly cartPage;
  constructor(private readonly page: Page) {
     this.searchPage = new SearchPage(page);
     this.productPage = new ProductPage(page);
     this.cartPage = new CartPage(page);
  }
 
  async validateHomePage(): Promise<void>{
     await expect(this.page).toHaveURL('/home');
  }
 
  async signIn():Promise<void>{
     await this.searchPage.signIn();
  }
 
  async loginIn(email:string,password:string):Promise<void>{
      await this.searchPage.loginIn(email,password);
  }

  async goToCatalog(catalogname:string): Promise<void> {
      await this.searchPage.goToCatalog(catalogname);
  }
  async search(name: string): Promise<void> {
    await this.searchPage.search(name);
  }
 
  async addToCart() :Promise<void>{
      await this.productPage.addToCart();
  }
 
  async proceedToCheckout():Promise<void>{
     await this.cartPage.proceedToCheckout();
  }
 
 async placeOrder():Promise<void>{
    await this.cartPage.placeOrder();
 }
}