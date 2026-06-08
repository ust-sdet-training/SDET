import { Page } from '@playwright/test';

export class SerchPage {

    constructor(private page: Page){}

    private box = () =>this.page.getByRole('searchbox');

    async search(product:string){
        await this.page.goto('/catalog');
        await this.box().fill(product);
        await this.page.getByRole('button',{name:'Search'}).click();
    }

    async openProduct(product:string){
        await this.page.getByRole('link',{name: product}).click();
    }
}