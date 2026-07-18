import { Page } from "@playwright/test";
import { DetailsLocators } from "../locators/DetailsLocator";
import { logger } from "../logger/logger";

export class DetailsPage {

    private readonly detailsLocators: DetailsLocators;

    constructor(page: Page) {

        this.detailsLocators = new DetailsLocators(page);

    }

    async enterFirstName(firstName: string) {
        logger.info("Entering passenger first name");
        await this.detailsLocators.firstName.fill(firstName);
    }

    async enterLastName(lastName: string){
        logger.info("Entering passenger last name");
        await this.detailsLocators.lastName.fill(lastName);
    }

    async selectGender(gender: string){
        logger.info(`Selecting passenger gender: ${gender}`);
        await this.detailsLocators.gender.selectOption(gender);
    }

    async enterEmail(email: string){
        logger.info("Entering passenger email");
        await this.detailsLocators.email.fill(email);
    }

    async enterPhoneNumber(phoneNumber: string){
        logger.info("Entering passenger phone number");
        await this.detailsLocators.phoneNumber.fill(phoneNumber);
    }

    async clickContinue(){
        logger.info("Continuing to payment step");
        await this.detailsLocators.continueButton.click();
    }

    async enterAge(age: number){
        logger.info(`Entering passenger age: ${age}`);
        await this.detailsLocators.age.fill(age.toString());
    }

    async enterPassengerDetails(
        firstName: string,
        lastName: string,
        age: number,
        gender: string,
        email: string,
        phoneNumber: string){
        logger.info("Starting passenger details flow");
        await this.enterFirstName(firstName);
        await this.enterLastName(lastName);
        await this.enterAge(age);
        await this.selectGender(gender);
        await this.enterEmail(email);
        await this.enterPhoneNumber(phoneNumber);
        await this.clickContinue();
        logger.info("Passenger details flow completed");
    }

}