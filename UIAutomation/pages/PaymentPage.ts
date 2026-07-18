import { Page } from '@playwright/test';

export class PaymentPage {

    constructor(private page: Page) {}

    async fillCardDetails(
        nameOnCard: string,
        cardNumber: string,
        expiry: string,
        cvv: string
    ) {
        await this.page.getByLabel('Name on card').fill(nameOnCard);
        await this.page.getByLabel('Card number').fill(cardNumber);
        await this.page.getByLabel('Expiry').fill(expiry);
        await this.page.getByLabel('CVV').fill(cvv);
    }

    async pay() {
        // Button text includes a dynamic amount ("Pay ₹1556.10"),
        // so match on the stable prefix rather than the full text.
        await this.page
            .getByRole('button', { name: /^Pay ₹/ })
            .click();
    }
}