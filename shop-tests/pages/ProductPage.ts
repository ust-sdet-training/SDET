import { Page, expect } from "@playwright/test";
 
export class ProductPage {
 
    constructor(readonly page: Page){}
 
    private size =this.page.getByRole('combobox',{name:'Size'});
    private quantity =this.page.getByLabel('Quantity');
    private addButton =this.page.getByRole('button',{name:'Add to cart'});
 
    async waitForPage(){
        await expect(this.page).toHaveURL(/\/product/);
    }
 
    async configure(size:string,color:string,qty:string,fulfilment:string){
 
        await this.size.selectOption({label:size});
        await expect(this.size).toHaveValue(size);
 
        await this.page.getByRole('group',{name:'Color'}).getByRole('radio',{name:color}).check();
        await expect(this.page.getByRole('group',{name:'Color'}).getByRole('radio',{name:color})).toBeChecked();
 
        await this.quantity.fill(qty);
        await expect(this.quantity).toHaveValue(qty);
 
        await this.page.getByRole('group',{name:'Fulfilment'}).getByRole('radio',{name:fulfilment}).check();
        await expect(this.page.getByRole('group',{name:'Fulfilment'}).getByRole('radio',{name:fulfilment})).toBeChecked();
    }
 
    async addToCart(){
 
        await Promise.all([
            this.page.waitForResponse(r =>r.url().includes('/cart/items')&&r.status()===201),
            this.addButton.click()
        ]);
 
 
        await expect(this.page).toHaveURL(/\/cart/);
 
    }
 
}