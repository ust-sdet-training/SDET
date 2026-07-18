import { Locator, Page } from "@playwright/test";

export class DetailsLocators {

    readonly firstName: Locator;
    readonly lastName: Locator;
    readonly age: Locator;
    readonly gender: Locator;

    readonly email: Locator;
    readonly phoneNumber: Locator;

    readonly continueButton: Locator;

    constructor(page: Page) {

        this.firstName =page.locator("input[id^='name-']");

        this.lastName = page.locator("input[id^='lastname-']");

        this.age = page.locator("input[id^='age-']");

        this.gender = page.locator("select[id^='gender-']");

        this.email = page.getByLabel("Email");

        this.phoneNumber = page.getByLabel("Phone number");

        this.continueButton = page.getByRole('button',{name: "Continue to payment"});

    }

}