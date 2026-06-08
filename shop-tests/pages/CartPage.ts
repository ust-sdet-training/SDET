import { Page ,Locator } from '@playwright/test';

export class CartPage {

    constructor(private readonly page: Page){}

    cartCount = () : Locator => this.page.getByTestId('cart-count');
    proceedToCheckoutButton = () : Locator => this.page.getByRole('button',{name:'Proceed to checkout'});

    async getCartCount():Promise<string>{
        return await this.cartCount().textContent() || '';
    }

    async clickOnProceedToCheckoutButton(){
        await this.proceedToCheckoutButton().click();
    }


}