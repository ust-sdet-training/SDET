import { Page ,Locator,expect } from '@playwright/test';

export class BagPage {

    constructor(private readonly page: Page){

    }

    placeOrderButton = () : Locator => this.page.getByRole('button', { name: 'Place Order' });
    loginPopupNumberInput = () : Locator => this.page.getByPlaceholder('Enter your Mobile Number');

    async clickPlaceOrder(){
        this.placeOrderButton().click();
    }

    async isLoginPopupDisplayed(){
        await expect(this.loginPopupNumberInput()).toBeVisible();
    }

}