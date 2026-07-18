import { expect, Locator, Page } from '@playwright/test';

export class PaymentPage {

    readonly page: Page;

    readonly cardName: Locator;
    readonly cardNumber: Locator;
    readonly expiry: Locator;
    readonly cvv: Locator;
    readonly payButton: Locator;
    readonly paymentError: Locator;

    constructor(page: Page) {

        this.page = page;

        this.cardName = page.locator('input[name="cardName"]');
        this.cardNumber = page.locator('input[name="cardNumber"]');
        this.cvv = page.locator('input[name="cardCvv"]');
        this.expiry = page.locator('input[name="cardExpiry"]');

        this.payButton = page.getByRole('button', {
            name: /pay/i
        });

        this.paymentError = page.locator(
            '[data-ref="payment-error"]'
        );
    }

    async verifyPaymentPage() {

        await expect(
            this.page.getByRole('heading', {
                name: /secure checkout/i
            })
        ).toBeVisible();
    }

    async enterCardDetails(
        name: string,
        number: string,
        expiry: string,
        cvv: string
    ) {

        await this.cardName.fill(name);
        await this.cardNumber.fill(number);
        await this.expiry.fill(expiry);
        await this.cvv.fill(cvv);

    }

    async payNow() {

        await this.payButton.click();

    }

    async isPaymentDeclined(): Promise<boolean> {

        try {

            await this.paymentError.waitFor({
                state: 'visible',
                timeout: 3000
            });

            return true;

        } catch {

            return false;

        }

    }

}