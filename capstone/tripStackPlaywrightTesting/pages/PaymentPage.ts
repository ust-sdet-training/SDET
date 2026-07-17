import { expect, Locator, Page } from '@playwright/test';
import { paymentData } from '../utils/testData';

export class PaymentPage {

    readonly page: Page;

    readonly nameOnCardTextbox: Locator;
    readonly cardNumberTextbox: Locator;
    readonly expiryTextbox: Locator;
    readonly cvvTextbox: Locator;
    readonly payButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.nameOnCardTextbox = page.getByRole('textbox', {
            name: 'Name on card'
        });

        this.cardNumberTextbox = page.getByRole('textbox', {
            name: 'Card number'
        });

        this.expiryTextbox = page.getByRole('textbox', {
            name: 'Expiry'
        });

        this.cvvTextbox = page.getByRole('textbox', {
            name: 'CVV'
        });

        this.payButton = page.getByRole('button', {
            name: 'Pay ₹'
        });

    }

    async verifyPaymentPageLoaded() {

        // await expect(this.nameOnCardTextbox).toBeVisible();
        await expect(this.cardNumberTextbox).toBeVisible();
        await expect(this.expiryTextbox).toBeVisible();
        await expect(this.cvvTextbox).toBeVisible();
        await expect(this.payButton).toBeVisible();

    }

    async enterCardHolderName(cardHolder: string) {
        await this.nameOnCardTextbox.fill(cardHolder);
    }

    async enterCardNumber(cardNumber: string) {
        await this.cardNumberTextbox.fill(cardNumber);
    }

    async enterExpiry(expiry: string) {
        await this.expiryTextbox.fill(expiry);
    }

    async enterCVV(cvv: string) {
        await this.cvvTextbox.fill(cvv);
    }

    async clickPay() {
        await this.payButton.click();
    }

    async makePayment() {

        await this.enterCardHolderName(paymentData.cardHolder);

        await this.enterCardNumber(paymentData.cardNumber);

        await this.enterExpiry(paymentData.expiry);

        await this.enterCVV(paymentData.cvv);

        await this.clickPay();

    }

}