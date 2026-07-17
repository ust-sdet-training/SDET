import { Locator, Page, expect } from "@playwright/test";

export class PaymentDetailsPage {

    constructor(readonly page: Page) { }

    private nameOnCardTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Name on card' });
    private cardNumberTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Card number' });
    private expiryDateTextBox = (): Locator => this.page.getByRole('textbox', { name: 'Expiry' });
    private cvvTextBox = (): Locator => this.page.getByRole('textbox', { name: 'CVV' });
    private payButton = (): Locator => this.page.getByRole('button', { name: 'Pay ₹' });

    async verifyPaymentDetailsPageLoaded() {
        await expect(this.page.url()).toContain('/book/payment');
        await expect(this.nameOnCardTextBox()).toBeVisible();
    }

    async fillPaymentDetails(nameOnCard:string,cardNumber:string,expiryDate:string,cvv:string) {
        await this.nameOnCardTextBox().fill(nameOnCard);
        await this.cardNumberTextBox().fill(cardNumber);
        await this.expiryDateTextBox().fill(expiryDate);
        await this.cvvTextBox().fill(cvv);
        await this.payButton().click();
    }

    
}