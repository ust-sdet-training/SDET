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

    // Booking Confirmation
    private bookingStatus = () => this.page.locator('[data-id="state"]');

    private pnr = () => this.page.locator('[data-id="pnr"]');

    // Payment Error (only if your application has one)
    private paymentError = () => this.page.getByRole('alert');

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

        const result = await Promise.race([

            this.bookingStatus()
                .waitFor({
                    state: 'visible',
                    timeout: 30000
                })
                .then(() => 'confirmed'),

            this.paymentError()
                .waitFor({
                    state: 'visible',
                    timeout: 30000
                })
                .then(() => 'error')

        ]).catch(() => 'timeout');

        if (result === 'confirmed') {

            console.log('Payment Successful');

            await expect(this.bookingStatus()).toHaveText(/Confirmed/i);

            await expect(this.pnr()).toBeVisible();

            return;
        }

        if (result === 'error') {

            const message =
                await this.paymentError().textContent();

            throw new Error(
                `Payment Failed : ${message}`
            );

        }

        throw new Error(
            'Payment timed out after waiting 30 seconds.'
        );

    }

    async makePayment() {

        await this.verifyPaymentPage();

        await this.enterCardDetails();

        await this.payNow();

    }

}