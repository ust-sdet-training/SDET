import { Locator, Page, expect } from "@playwright/test";

export class PassengerDetailsPage {

    constructor(readonly page: Page) { }

    private firstNameTextBox = () : Locator => this.page.locator("//input[contains(@name,'firstName')]");
    private lastNameTextBox = () : Locator => this.page.locator("//input[contains(@name,'lastName')]");
    private ageSpinButton = () : Locator => this.page.locator("//input[contains(@name,'passengerAge')]");;
    private genderLabel = () : Locator => this.page.locator("//select[contains(@name,'passengerGender')]");
    private emailTextBox = () : Locator => this.page.getByRole('textbox', { name: 'Email' });
    private phoneNumberTextBox = () : Locator => this.page.getByRole('textbox', { name: 'Phone number' });
    private continueToPaymentButton = () : Locator => this.page.getByRole('button', { name: 'Continue to payment' });

    async verifyPassengerDetailsPageLoaded() {
        await expect(this.page.url()).toContain('/book/passenger');
        await expect(this.firstNameTextBox()).toBeVisible();
    }

    async fillPassengerDetails(firstName:string,lastName:string,age:string,gender:string,email:string,phoneNumber:string) {
        await this.firstNameTextBox().fill(firstName);
        await this.lastNameTextBox().fill(lastName);
        await this.ageSpinButton().fill(age);
        await this.genderLabel().selectOption(gender);
        await this.emailTextBox().fill(email);
        await this.phoneNumberTextBox().fill(phoneNumber);
        await this.continueToPaymentButton().click();
    }

  

    

}