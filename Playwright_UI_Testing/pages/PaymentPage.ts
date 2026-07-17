import { expect, Page } from "@playwright/test";
import { PaymentLocators } from "../locators/PaymentLocators";

export class PaymentPage {

    private locators: PaymentLocators;

    constructor(private page: Page) {
        this.locators = new PaymentLocators(page);
    }

    async verifyPaymentPage() {
        await expect(this.locators.cardNameInput()).toBeVisible();
        await expect(this.locators.cardNumberInput()).toBeVisible();
        await expect(this.locators.expiryInput()).toBeVisible();
        await expect(this.locators.cvvInput()).toBeVisible();
        await expect(this.locators.payButton()).toBeVisible();
    }

    async enterCardName(cardName: string) {
        await this.locators.cardNameInput().fill(cardName);
    }

    async enterCardNumber(cardNumber: string) {
        await this.locators.cardNumberInput().fill(cardNumber);
    }

    async enterExpiry(expiry: string) {
        await this.locators.expiryInput().fill(expiry);
    }

    async enterCVV(cvv: string) {
        await this.locators.cvvInput().fill(cvv);
    }

    async clickPay() {
        await this.locators.payButton().click();
    }

    async makePayment(
        cardName: string,
        cardNumber: string,
        expiry: string,
        cvv: string
    ) {
        await this.enterCardName(cardName);
        await this.enterCardNumber(cardNumber);
        await this.enterExpiry(expiry);
        await this.enterCVV(cvv);
        await this.clickPay();
    }
}