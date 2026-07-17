import { expect, Page } from "@playwright/test";
import { Logger } from "../src/logger/logger";

export class PaymentPage {

    constructor(private page: Page) {}

    private cardName = () =>
        this.page.locator("#cardName");

    private cardNumber = () =>
        this.page.locator("#cardNumber");

    private cardExpiry = () =>
        this.page.locator("#cardExpiry");

    private cardCvv = () =>
        this.page.locator("#cardCvv");

    private payButton = () =>
        this.page.getByRole("button", {
            name: /pay/i
        });

    async verifyPaymentPage() {

        Logger.info("Verifying Payment Page");

        await expect(this.cardName()).toBeVisible();
        await expect(this.cardNumber()).toBeVisible();
        await expect(this.cardExpiry()).toBeVisible();
        await expect(this.cardCvv()).toBeVisible();

    }

    async enterCardDetails(
        cardName: string,
        cardNumber: string,
        expiry: string,
        cvv: string
    ) {

        Logger.info("Entering Card Holder Name");
        await this.cardName().fill(cardName);

        Logger.info("Entering Card Number");
        await this.cardNumber().fill(cardNumber);

        Logger.info("Entering Expiry");
        await this.cardExpiry().fill(expiry);

        Logger.info("Entering CVV");
        await this.cardCvv().fill(cvv);

    }

    async payNow() {

        Logger.info("Clicking Pay Button");

        await this.payButton().click();

    }

}