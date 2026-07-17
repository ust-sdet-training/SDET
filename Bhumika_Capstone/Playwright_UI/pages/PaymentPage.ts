import { Page } from "@playwright/test";
import { PaymentLocators } from "../locators/PaymentLocators";
import { logger } from "../logger/Logger";

export class PaymentPage {
  private locators: PaymentLocators;

  constructor(private page: Page) {
    this.locators = new PaymentLocators(page);
  }

  async fillPaymentDetails(
    cardHolderName: string,
    cardNumber: string,
    expiry: string,
    cvv: string,
  ) {
    const nameField = this.locators.nameOnCardInput();
    await nameField.waitFor({ state: "visible" });
    logger.info(`Filling card holder name: ${cardHolderName}`);
    await nameField.fill(cardHolderName || "");

    logger.info(
      `Filling card number: ${cardNumber.replace(/.(?=.{4})/g, "*")}`,
    );
    await this.locators.cardNumberInput().waitFor({ state: "visible" });
    await this.locators.cardNumberInput().fill(cardNumber);

    logger.info(`Filling expiry: ${expiry}`);
    await this.locators.expiryInput().waitFor({ state: "visible" });
    await this.locators.expiryInput().fill(expiry);

    logger.info("Filling CVV");
    await this.locators.cvvInput().waitFor({ state: "visible" });
    await this.locators.cvvInput().fill(cvv);
  }

  async payNow() {
    logger.info("Clicking Pay button");
    await this.locators.payButton().click();
  }
}
