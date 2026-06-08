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
  
  async sign_in():Promise<void>{
     await this.searchPage.sign_in();
  }

  async login_in(email:string,password:string):Promise<void>{
      await this.searchPage.login_in(email,password);
  }

  async invalid_login_in(email:string,password:string):Promise<void>{
       await this.searchPage.invalid_login_in(email,password);
  }

  async goToCatalog(catalogname:string): Promise<void> {
      await this.searchPage.goToCatalog(catalogname);
  }
  async search(name: string): Promise<void> {
    await this.searchPage.search(name);
  }

  async addToCart() :Promise<void>{
      await this.productPage.addtocart();
  }

 async invalid_quantity():Promise<void>{
     await this.productPage.invalid_quantity()
 }

  async proceed_to_checkout():Promise<void>{
     await this.cartPage.proceed_to_checkout();
  }

 async place_order():Promise<void>{
    await this.cartPage.place_order();
 }
}