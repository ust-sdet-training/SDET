import { expect, Page } from '@playwright/test';

export class PaymentPage {

    constructor(
        private page: Page
    ) {}

    async verifyPaymentPage() {

        await expect(
            this.page
        ).toHaveURL(
            /payment/
        );
    }
}