import { Page } from '@playwright/test';

export class PassengerPage {

    constructor(
        private page: Page
    ) {}

    async enterPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {

        await this.page
            .locator('#name-L3')
            .fill(firstName);

        await this.page
            .locator('#lastname-L3')
            .fill(lastName);

        await this.page
            .locator('#age-L3')
            .fill(age);

        await this.page
            .locator('#gender-L3')
            .selectOption(gender);

        await this.page
            .locator('#email')
            .fill(email);

        await this.page
            .locator('#phone')
            .fill(phone);
    }

    async continueToPayment() {

        await this.page
            .getByRole(
                'button',
                {
                    name: 'Continue to payment'
                }
            )
            .click();
    }
}