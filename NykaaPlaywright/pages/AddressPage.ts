import { Page, Locator } from '@playwright/test';

export class AddressPage {

    constructor(readonly page: Page) { }


    pincodeInput = (): Locator => this.page.getByRole('spinbutton', { name: 'Pincode' });
    houseFlatOfficeInput = (): Locator => this.page.getByRole('textbox', { name: 'House/ Flat/ Office No.' });
    roadAreaColonyInput = (): Locator => this.page.getByRole('textbox', { name: 'Road Name/ Area /Colony' });
    nameInput = (): Locator => this.page.getByTestId('txt_name').getByRole('textbox', { name: 'Name' });
    phoneNumberInput = (): Locator => this.page.getByRole('spinbutton', { name: 'Phone' });
    emailINput = (): Locator => this.page.getByRole('textbox', { name: 'Email' });
    shipToThisAddressButton = (): Locator => this.page.getByRole('button', { name: 'SHIP TO THIS ADDRESS' });



    async fillAddress(pincode: string, houseflatOfficeNo: string, roadAreaColony: string, name: string, phone: string, email: string) {

        await this.pincodeInput().fill(pincode);
        await this.houseFlatOfficeInput().click();
        await this.page.waitForTimeout(2000);
        await this.houseFlatOfficeInput().fill(houseflatOfficeNo);
        await this.roadAreaColonyInput().fill(roadAreaColony);
        await this.nameInput().fill(name);
        await this.phoneNumberInput().fill(phone);
        await this.emailINput().fill(email);


    }

    async clickShipToThisAddress() {
        await this.shipToThisAddressButton().click();
        await this.page.waitForTimeout(2000);
    }



}