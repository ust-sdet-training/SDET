import { Page } from "@playwright/test";

export class PassengerDetailsLocators {

    constructor(private page: Page) {}

    firstNameInput = () =>
        this.page.locator("#name-U4");

    lastNameInput = () =>
        this.page.locator("#lastname-U4");

    ageInput = () =>
        this.page.locator("#age-U4");

    genderDropdown = () =>
        this.page.locator("#gender-U4");

    emailInput = () =>
        this.page.locator("#email");

    phoneInput = () =>
        this.page.locator("#phone");

    continueButton = () =>
        this.page.getByRole("button", {
            name: "Continue to payment"
        });
}