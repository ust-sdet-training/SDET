import {Page} from '@playwright/test'
export class PaymentPage
{
    constructor (public readonly page:Page)
    {

    }
    async enterPaymentDetails(firstname:string,cardnumber:string,exp:string,cv:string)
    {
        await this.page.getByRole('textbox', { name: 'Name on card' }).click();
  await this.page.getByRole('textbox', { name: 'Name on card' }).fill(firstname);
  await this.page.getByRole('textbox', { name: 'Card number' }).click();
  await this.page.getByRole('textbox', { name: 'Card number' }).fill(cardnumber);
  await this.page.getByRole('textbox', { name: 'Expiry' }).click();
  await this.page.getByRole('textbox', { name: 'Expiry' }).fill(exp);
  await this.page.getByRole('textbox', { name: 'CVV' }).click();
  await this.page.getByRole('textbox', { name: 'CVV' }).fill(cv);
  await this.page.getByRole('button', { name: 'Pay ₹' }).click();




    }
}