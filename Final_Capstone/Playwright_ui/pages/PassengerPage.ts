import { Page, expect } from '@playwright/test';
import { Logger } from '../src/logger/logger';

export class PassengerPage {

    constructor(private page: Page) {}

    private firstName = () =>
        this.page.getByLabel(/First name/i);

    private lastName = () =>
        this.page.getByLabel(/Last name/i);

    private age = () =>
        this.page.getByLabel(/Age/i);

    private gender = () =>
        this.page.getByLabel(/Gender/i);

    private email = () =>
        this.page.getByLabel(/Email/i);

    private phone = () =>
        this.page.getByLabel(/Phone number/i);

    private continueButton = () =>
        this.page.getByRole('button', {
            name: /Continue to payment/i
        });

    async verifyPassengerPage() {

        await expect(
            this.page.getByRole('heading', {
                name: /Who's travelling/i
            })
        ).toBeVisible();

    }

    async enterPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {

        Logger.info("Entering Passenger Details");

        await this.firstName().fill(firstName);

        await this.lastName().fill(lastName);

        await this.age().fill(age);

        await this.gender().selectOption(gender);

        await this.email().fill(email);

        await this.phone().fill(phone);

    }

    async continueToPayment() {

        Logger.info("Proceeding to Payment");

        await this.continueButton().click();

    }

}