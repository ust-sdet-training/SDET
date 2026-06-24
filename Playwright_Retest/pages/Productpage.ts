import { Page } from "@playwright/test";
export class ProductPage{
    constructor(private page:Page){}
    async addtobag(){
        await this.page.getByRole('button',{name:'Add to bag undefined'})
    }
}