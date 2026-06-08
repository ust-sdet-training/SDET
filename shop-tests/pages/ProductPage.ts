import {expect,Page,Locator} from '@playwright/test';
import { SearchPage } from './SearchPage';

export class ProductPage
{
    constructor(private readonly page:Page){};
  
    results = ()=> this.page.getByTestId('cart-count');

  async open():Promise<void> {
    await this.page.goto("/catalog");
  }

   async addCart(searchkey:string):Promise<void>
   {
        var search = new SearchPage(this.page);
         await search.search(searchkey);

         await this.page.getByRole('link',{name:`View ${searchkey}`}).click();
         const addPromise = this.page.waitForResponse(res => res.url().includes('/cart') && res.status()===200)
         await this.page.getByRole('button',{name:'Add to cart'}).click();
         await addPromise;
   }
}
