import {Page} from '@playwright/test'
export class PassengerDetailsPage
{
    constructor (public readonly page:Page)
    {

    }
    async enterPassengerDetails(firstname:string,lastname:string,age:string,email:string,ph_no:string)
    {
        await this.page.getByRole('textbox', { name: 'First name' }).click();
        await this.page.getByRole('textbox', { name: 'First name' }).fill(firstname);
        await this.page.getByRole('textbox', { name: 'Last name' }).click();
        await this.page.getByRole('textbox', { name: 'Last name' }).fill(lastname);
        await this.page.getByRole('spinbutton', { name: 'Age' }).click();
        await this.page.getByRole('spinbutton', { name: 'Age' }).fill(age);
        await this.page.getByLabel('Gender ').selectOption('male');
        await this.page.getByRole('textbox', { name: 'Email' }).click();
        await this.page.getByRole('textbox', { name: 'Email' }).fill(email);
        await this.page.getByRole('textbox', { name: 'Phone number' }).click();
        await this.page.getByRole('textbox', { name: 'Phone number' }).fill(ph_no);
         await this.page.getByRole('button', { name: 'Continue to payment' }).click();

    }
}