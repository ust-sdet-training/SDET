import {expect, type Page} from '@playwright/test'
import { ShopFlow } from './flows/Shopflows';
import { ProductPage} from './ProductPage';
export class SearchPage{
    constructor(private readonly page:Page){}

    
    private box =() => this.page
        .getByRole('searchbox');
    results =() => this.page 
        .getByTestId('product-card');

    button =() => this.page.getByRole('button',{name:"Search"});    

    sign_in_button =() => this.page.getByRole('link',{name:"Sign in"});

     async sign_in(){
      
          await this.sign_in_button().click();
          await expect(this.page).toHaveURL('/login');
     }

     async login_in(email:string,password:string): Promise<void>
       {
            const res = this.page.waitForResponse(r => r.url().includes('/login') && r.status() === 200);
           await this.page.getByLabel("Email").fill(email);
           await this.page.getByLabel("Password").fill(password);
           await this.page.getByRole("button", { name: "Sign in" }).click();
           await res;
       }
      async invalid_login_in(email:string,password:string): Promise<void>
       {
           await this.page.getByLabel("Email").fill(email);
           await this.page.getByLabel("Password").fill(password);
           await this.page.getByRole("button", { name: "Sign in" }).click();
           await expect(this.page.getByRole("alert")).toContainText(/invalid credentials/i);
       }
    
       
   

   async goToCatalog(catalogname:string): Promise<void>{
        const response = this.page.waitForResponse(r => r.url().includes('/products') && r.status() === 200);
        const PrimaryNavigation = this.page.getByRole('navigation',{name:"Primary navigation"});
        await PrimaryNavigation.getByRole('link',{name:catalogname}).click();
        await response;


     }
  

   async search(q: string): Promise<void> {
    const res = this.page.waitForResponse(
        r => r.url().includes('/products') && r.status() === 200
    );

    await this.box().fill(q);
    await this.box().press('Enter');
    await res;

    await this.results()
        .first()
        .getByRole('link', { name: "View Running Shoes" })
        .click();
}
         
    }


    
