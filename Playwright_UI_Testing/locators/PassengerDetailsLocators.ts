import { Page } from "@playwright/test";

export class PassengerDetailsLocators {

    constructor(private page: Page) {}

    firstNameInput = () =>
        this.page.locator("#name-L5");

    lastNameInput = () =>
        this.page.locator("#lastname-L5");

    ageInput = () =>
        this.page.locator("#age-L5");

    genderDropdown = () =>
        this.page.locator("#gender-L5");

    emailInput = () =>
        this.page.locator("#email");

    phoneInput = () =>
        this.page.locator("#phone");

    continueButton = () =>
        this.page.getByRole("button", {
            name: "Continue to payment"
        });
}