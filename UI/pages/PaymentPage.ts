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

    async getTripIdorg():Promise<string>{
       var tripId =  await this.page.locator('[data-id="pnr"]').textContent();

       
    if (tripId === null) {
            throw new Error('Trip ID not found');
        }

    return tripId;

    }

    async getTripId(): Promise<string> {
    const pnrLocator = this.page.locator('[data-id="pnr"]');
    const alertLocator = this.page.getByRole('alert');

    const result = await Promise.race([
        pnrLocator.waitFor({ state: 'visible' }).then(() => 'SUCCESS'),
        alertLocator.waitFor({ state: 'visible' }).then(() => 'ERROR')
    ]);

    if (result === 'ERROR') {
        const msg = await alertLocator.textContent();

        if (/payment gateway timed out/i.test(msg ?? '')) {
            throw new Error('Payment gateway timed out');
        }

        throw new Error(`Payment failed: ${msg}`);
    }

    const tripId = (await pnrLocator.textContent())?.trim();

    if (!tripId) {
        throw new Error('Trip ID not found');
    }

    return tripId;
}



}