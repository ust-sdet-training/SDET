import { Page } from "@playwright/test";
import { ConfirmationLocators } from "../locators/ConfirmationLocators";

export class ConfirmationPage {
  private locators: ConfirmationLocators;

  constructor(private page: Page) {
    this.locators = new ConfirmationLocators(page);
  }

  async verifyConfirmation() {
    await this.locators.confirmationHeading().waitFor({ state: "visible" });
    await this.locators.confirmationText().waitFor({ state: "visible" });
  }
}
