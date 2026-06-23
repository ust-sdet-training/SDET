import { Page ,Locator } from '@playwright/test';

export class ProductListingPage {

    constructor(private readonly page: Page){

    }

    brandsFilter = () : Locator => this.page.getByRole('button', { name: 'Brands plus_icon' });
    brandNameOption = (brandName: string) : Locator => this.page.getByLabel('Brands').getByText(brandName);
    productCard = (cardName: string) => this.page.getByRole('link', { name: `product card ${cardName}` });


    async filterByBrand(){
        await this.brandsFilter().click();
    }

    async chooseBrand(name: string){
        await this.brandNameOption(name).click();
    }

    async selectProduct(card: string){
        this.productCard(card).click();
    }
}