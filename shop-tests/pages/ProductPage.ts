import {Page,Locator} from '@playwright/test';

export class ProductPage{

    constructor(private readonly page:Page){}

    addToCartButton = () : Locator => this.page.getByRole('button',{name:'Add to cart'});
    productHeading = (name: string) : Locator => this.page.getByRole('heading',{name:`${name}`});
    sizeInput = () : Locator => this.page.getByLabel('Size');
    colorInput = (color: string) : Locator => this.page.getByRole('radio',{name:`${color}`});
    quantityInput = () : Locator => this.page.getByLabel('Quantity');
    fulfilmentInput = (fulfil: string) : Locator => this.page.getByRole('radio',{name:`${fulfil}`});

    async clickAddToCartButton(){
        await this.addToCartButton().click(); 
    }

    async getProductHeading(name: string):Promise<string>{
        return await this.productHeading(name).textContent() || '';
    }

    async selectSize(size: string){
        await this.sizeInput().selectOption(size);
    }

    async selectColor(color: string){
        await this.colorInput(color).click();
    }

    async selectQuantity(quantity: string){
        await this.quantityInput().fill(quantity);
    }

    async selectfulfilment(fulfil: string){
        await this.fulfilmentInput(fulfil).click();
    }






    


}