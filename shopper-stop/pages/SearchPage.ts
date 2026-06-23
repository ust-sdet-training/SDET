import {type Page,expect} from '@playwright/test';

export class SearchPage{
    constructor(private readonly page: Page) {}

    private box=()=> this.page.getByRole('searchbox');
    results = () => this.page.getByTestId('result');
    async search(q:string){
        const res=this.page.waitForResponse(r=>r.url().includes('/products') && r.status()===200);
        await this.box().fill(q);
        await this.box().press('Enter');
        await res;
    }
    async addFirstResult(){
        const added = this.page.waitForResponse((response)=>{
        return response.url().includes('/cart') && response.status() === 200;
    });
        await this.page.getByRole('button',{name:'Add to cart'}).click();  
        await added;
    }


}