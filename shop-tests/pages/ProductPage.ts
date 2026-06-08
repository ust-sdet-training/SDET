import { Page } from '@playwright/test';

export class ProductPage {

    constructor(private page: Page){}

    async selectSize(size:string){
        await this.page.getByLabel('size').selectOption({label:size});
    }

    async addToCart(){
        const responsePromise =this.page.waitForResponse(response =>response.url().includes('/cart') &&response.status() === 200);
        await this.page.getByRole('button',{name:'Add to cart'}).click();
        await responsePromise;
    }
}