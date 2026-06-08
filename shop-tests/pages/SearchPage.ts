import {expect, type Page} from '@playwright/test'
import { ShopFlow } from '../flows/ShopFlow';
import { ProductPage} from './ProductPage';
export class SearchPage{
    constructor(private readonly page:Page){}
    private box =() => this.page
        .getByRole('searchbox');
    results =() => this.page.getByTestId('product-card');
    button =() => this.page.getByRole('button',{name:"Search"});    
    sign_in_button =() => this.page.getByRole('link',{name:"Sign in"});
     async signIn(){
          await this.sign_in_button().click();
     }
     
      async loginIn(email:string,password:string): Promise<void>
       {
            const res = this.page.waitForResponse(r => r.url().includes('/login') && r.status() === 200);
           await this.page.getByLabel("Email").fill(email);
           await this.page.getByLabel("Password").fill(password);
           await this.page.getByRole("button", { name: "Sign in" }).click();
           await res;
       }
       
   async goToCatalog(catalogname:string): Promise<void>{
        const PrimaryNavigation = this.page.getByRole('navigation',{name:"Primary navigation"});
      await PrimaryNavigation.getByRole('link',{name:catalogname}).click();
     }
   async search(q: string): Promise<void> {
    const res = this.page.waitForResponse(
    r => r.url().includes('/products') && r.status() === 200);
    await this.box().fill(q);
    await this.box().press('Enter');
    await res;
    await this.results()
        .first()
        .getByRole('link', { name: "View Running Shoes" })
        .click();
}
         
}