import { expect, Locator, Page } from '@playwright/test';

export class PassengerPage {

    readonly page: Page;

    readonly firstNameTextbox: Locator;
    readonly lastNameTextbox: Locator;
    readonly ageTextbox: Locator;
    readonly emailTextbox: Locator;
    readonly phoneTextbox: Locator;
    readonly continueToPaymentButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.firstNameTextbox = page.getByRole('textbox', {
            name: /First name/i
        });

        this.lastNameTextbox = page.getByRole('textbox', {
            name: /Last name/i
        });

        this.ageTextbox = page.getByRole('spinbutton', {
            name: /Age/i
        });

        this.emailTextbox = page.getByRole('textbox', {
            name: 'Email'
        });

        this.phoneTextbox = page.getByRole('textbox', {
            name: 'Phone number'
        });

        this.continueToPaymentButton = page.getByRole('button', {
            name: 'Continue to payment'
        });

    }

    async verifyPassengerPageLoaded() {

        await expect(this.firstNameTextbox).toBeVisible();
        await expect(this.lastNameTextbox).toBeVisible();
        await expect(this.ageTextbox).toBeVisible();
        await expect(this.emailTextbox).toBeVisible();
        await expect(this.phoneTextbox).toBeVisible();

    }

    async enterFirstName(firstName: string) {
        await this.firstNameTextbox.fill(firstName);
    }

    async enterLastName(lastName: string) {
        await this.lastNameTextbox.fill(lastName);
    }

    async enterAge(age: string) {
        await this.ageTextbox.fill(age);
    }

    async enterEmail(email: string) {
        await this.emailTextbox.fill(email);
    }

    async enterPhone(phone: string) {
        await this.phoneTextbox.fill(phone);
    }

    async clickContinueToPayment() {

    await expect(this.continueToPaymentButton).toBeEnabled();

    await this.continueToPaymentButton.click();

    await this.page.waitForTimeout(2000);

    console.log("Current URL:", this.page.url());

}

    async fillPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        email: string,
        phone: string
    ) {

        await this.enterFirstName(firstName);
        await this.enterLastName(lastName);
        await this.enterAge(age);
        await this.enterEmail(email);
        await this.enterPhone(phone);

    }

}