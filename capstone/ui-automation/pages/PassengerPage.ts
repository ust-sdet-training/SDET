import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";

export class PassengerPage extends BasePage {

    readonly firstNameTextbox: Locator;
    readonly lastNameTextbox: Locator;
    readonly ageTextbox: Locator;
    readonly genderDropdown: Locator;
    readonly emailTextbox: Locator;
    readonly phoneTextbox: Locator;
    readonly continueButton: Locator;

    constructor(page: Page) {
        super(page);

        this.firstNameTextbox = page.getByLabel("First Name");
        this.lastNameTextbox = page.getByLabel("Last Name");
        this.ageTextbox = page.getByLabel("Age");
        this.genderDropdown = page.getByLabel("Gender");
        this.emailTextbox = page.getByLabel("Email");
        this.phoneTextbox = page.getByLabel("Phone Number");
        this.continueButton = page.getByRole("button", { name: "Continue to payment" });
    }

    async fillPassengerDetails(passenger: {
        firstName: string;
        lastName: string;
        age: number;
        gender: string;
        email: string;
        phoneNumber: string;
    }) {
        logger.info("[PassengerPage] Filling passenger details");
        await this.fill(this.firstNameTextbox, passenger.firstName, "first name");
        await this.fill(this.lastNameTextbox, passenger.lastName, "last name");
        await this.fill(this.ageTextbox, passenger.age.toString(), "age");
        await this.genderDropdown.selectOption({
            label: passenger.gender
        });
        await this.fill(this.emailTextbox, passenger.email, "email");
        await this.fill(this.phoneTextbox, passenger.phoneNumber, "phone number");
    }

    async continueBooking() {
        logger.info("[PassengerPage] Continuing to payment");
        await this.click(this.continueButton, "Continue button");
    }

}