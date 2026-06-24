import {Page,Locator,expect} from '@playwright/test';
import { product } from '../test-data/product';

export class ProductListingPage {

    constructor(readonly page:Page){}

    searchedproductHeading = (productName: string) : Locator => this.page.getByRole('heading', { name: `Buy ${productName} Products Online,` });
    productCard = (card: string) : Locator => this.page.getByRole('link', { name: `${card}` });
    addToBag = (product: string) : Locator => this.page.getByRole('button', { name: `Add to bag ${product}` });
    addedToBag = () : Locator => this.page.getByRole('button', { name: 'Added to Bag ❯' });
    proceedButton = () : Locator => this.page.getByRole('link',{name:'Proceed'});
    

    async isProductListingPageDisplayedWithProducts(withProduct: string){
        expect(this.searchedproductHeading(withProduct)).toBeVisible();
    }

    async hoverToProductCard(card:string){
        await this.productCard(card).hover();
        await this.productCard(card).hover();
    }

    async addProductToBag(product: string){
        await this.addToBag(product).click();
    }

    async clickAddedToBag(){
        await this.addedToBag().click();
    }

    async clickProceedButton(){
        await this.proceedButton().click();
    }


    




}