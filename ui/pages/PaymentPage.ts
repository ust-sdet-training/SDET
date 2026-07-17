import { expect, Page } from '@playwright/test';
import { paymentData } from '../data/payment';
import { BasePage } from './BasePage';

export class PaymentPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }
    // Locators

    private cardName = () => this.page.locator('#cardName');

    private cardNumber = () => this.page.locator('#cardNumber');

    private expiry = () => this.page.locator('#cardExpiry');

    private cvv = () => this.page.locator('#cardCvv');

    private payButton = () => this.page.locator('button[type="submit"]');

    async verifyPaymentPage() {

        await expect(this.page).toHaveURL(/book\/payment/);

    }

    async enterCardDetails() {

        await this.cardName().fill(paymentData.cardName);

        await this.cardNumber().fill(paymentData.cardNumber);

        await this.expiry().fill(paymentData.expiry);

        await this.cvv().fill(paymentData.cvv);

    }

    async payNow() {

        await this.payButton().click();

    }

    async makePayment() {

        await this.verifyPaymentPage();

        await this.enterCardDetails();

        await this.payNow();

    }

}