import { Page } from '@playwright/test';

export class PassengerPage {

    constructor(private page: Page) {}

    async fillTravellerDetails(
        seatId: string,
        firstName: string,
        lastName: string,
        age: string,
        gender: 'Male' | 'Female' | string
    ) {
        await this.page.getByLabel(`First name (seat ${seatId})`).fill(firstName);
        await this.page.getByLabel(`Last name (seat ${seatId})`).fill(lastName);
        await this.page.getByLabel(`Age (seat ${seatId})`).fill(age);
        await this.page.getByLabel(`Gender (seat ${seatId})`).selectOption(gender);
    }

    async fillContactDetails(email: string, phone: string) {
        await this.page.getByLabel('Email').fill(email);
        await this.page.getByLabel('Phone number').fill(phone);
    }

    async continueToPayment() {
        await this.page
            .getByRole('button', { name: 'Continue to payment' })
            .click();
    }
}