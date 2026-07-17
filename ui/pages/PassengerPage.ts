import { expect, Page } from '@playwright/test';
import { passengerData } from '../data/passenger';
import { BasePage } from './BasePage';

export class PassengerPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }
    // Locators
    private firstName = () => this.page.locator('input[name^="firstName_"]');

    private lastName = () => this.page.locator('input[name^="lastName_"]');

    private age = () => this.page.locator('input[name^="passengerAge_"]');

    private gender = () => this.page.locator('select[name^="passengerGender_"]');

    private email = () => this.page.locator('#email');

    private phone = () => this.page.locator('#phone');

    private continueButton = () => this.page.locator('button[type="submit"]');

    async verifyPassengerPage() {

        await expect(this.page).toHaveURL(/book\/passenger/);

    }

    async enterPassengerDetails() {

        await this.firstName().fill(passengerData.firstName);

        await this.lastName().fill(passengerData.lastName);

        await this.age().fill(passengerData.age);

        await this.gender().selectOption(passengerData.gender);

    }

    async enterContactDetails() {

        await this.email().fill(process.env.EMAIL!);

        await this.phone().fill(process.env.PHONE!);

    }

    async continueToPayment() {

        await this.continueButton().click();

    }

    async fillPassengerDetails() {

        await this.verifyPassengerPage();

        await this.enterPassengerDetails();

        await this.enterContactDetails();

        await this.continueToPayment();

        await expect(this.page).toHaveURL(/book\/payment/);

    }

}