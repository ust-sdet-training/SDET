import { Page } from "@playwright/test";
import { PassengerLocators } from "../locators/PassengerLocators";
import { logger } from "../logger/Logger";

export class PassengerPage {
  private locators: PassengerLocators;

  constructor(private page: Page) {
    this.locators = new PassengerLocators(page);
  }

  async fillPassengerDetails(
    firstName: string,
    lastName: string,
    age: number,
    gender: string,
    email: string,
    phone: string,
  ) {
    const firstNameField = this.locators.firstNameInput();
    await firstNameField.waitFor({ state: "visible" });
    logger.info(`Filling first name: ${firstName}`);
    await firstNameField.fill(firstName);

    const lastNameField = this.locators.lastNameInput();
    await lastNameField.waitFor({ state: "visible" });
    logger.info(`Filling last name: ${lastName}`);
    await lastNameField.fill(lastName);

    const ageField = this.locators.ageInput();
    await ageField.waitFor({ state: "visible" });
    logger.info(`Filling age: ${age}`);
    await ageField.fill(age.toString());

    const genderSelect = this.locators.genderSelect();
    await genderSelect.waitFor({ state: "visible" });
    logger.info(`Selecting gender: ${gender}`);
    await genderSelect.selectOption({ label: gender });

    const emailField = this.locators.emailInput();
    await emailField.waitFor({ state: "visible" });
    logger.info(`Filling email: ${email}`);
    await emailField.fill(email);

    const phoneField = this.locators.phoneInput();
    await phoneField.waitFor({ state: "visible" });
    logger.info(`Filling phone: ${phone}`);
    await phoneField.fill(phone);
  }

  async continueToPayment() {
    const continueButton = this.locators.continueButton();
    await continueButton.waitFor({ state: "visible" });

    await continueButton.click();
  }
}
