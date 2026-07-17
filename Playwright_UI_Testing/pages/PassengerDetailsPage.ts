import { expect, Page } from "@playwright/test";
import { PassengerDetailsLocators } from "../locators/PassengerDetailsLocators";

export class PassengerDetailsPage {

    private locators: PassengerDetailsLocators;

    constructor(private page: Page) {
        this.locators = new PassengerDetailsLocators(page);
    }

    async verifyPassengerDetailsPage() {
        await expect(this.locators.firstNameInput()).toBeVisible();
    }

    async enterFirstName(firstName: string) {
        await this.locators.firstNameInput().fill(firstName);
    }

    async enterLastName(lastName: string) {
        await this.locators.lastNameInput().fill(lastName);
    }

    async enterAge(age: string) {
        await this.locators.ageInput().fill(age);
    }

    async selectGender(gender: string) {
        await this.locators.genderDropdown().selectOption(gender);
    }

    async enterEmail(email: string) {
        await this.locators.emailInput().fill(email);
    }

    async enterPhone(phone: string) {
        await this.locators.phoneInput().fill(phone);
    }

    async clickContinue() {
        await this.locators.continueButton().click();
    }

    async enterPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {
        await this.enterFirstName(firstName);
        await this.enterLastName(lastName);
        await this.enterAge(age);
        await this.selectGender(gender);
        await this.enterEmail(email);
        await this.enterPhone(phone);

        await this.clickContinue();
    }
}