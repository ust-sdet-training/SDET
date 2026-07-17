import {Page} from "@playwright/test"

import {CardDetailsModel} from '../test-data/CardDetailsModel'

export class PaymentPage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }


    async setCardDetails(clientdetails : CardDetailsModel){

        await this.page.getByRole('textbox', { name: 'Name on card' }).fill(clientdetails.name);
        await this.page.getByRole('textbox', { name: 'Name on card' }).click();
        await this.page.getByRole('textbox', { name: 'Card number' }).click();
        await this.page.getByRole('textbox', { name: 'Card number' }).fill(clientdetails.cardnumber);
        await this.page.getByRole('textbox', { name: 'Expiry' }).click();
        await this.page.getByRole('textbox', { name: 'Expiry' }).fill(clientdetails.expirydate);
        await this.page.getByRole('textbox', { name: 'CVV' }).click();
        await this.page.getByRole('textbox', { name: 'CVV' }).fill(clientdetails.cvv);

    }

    async payPrice(){
        await this.page.getByRole('button', { name: 'Pay ₹' }).click();
    }

    async getTripId(){
        return await this.page.locator('[data-id="pnr"]').textContent();
    }



}