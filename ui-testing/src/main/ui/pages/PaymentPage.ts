import { expect, Page } from "@playwright/test";
import { secrets } from "../../utils/secrets";

export class PaymentPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
    }

    
    async payment(CARD_NUMBER: string, CARD_EXPIRY: string, CARD_CVV: string): Promise<void> {
        await this.page.getByRole('textbox', { name: 'Name on card' }).fill('Bob Tedd');
        await this.page.getByRole('textbox', { name: 'Card number' }).fill(CARD_NUMBER);
        await this.page.getByRole('textbox', { name: 'Expiry' }).fill(CARD_EXPIRY || '');
        await this.page.getByRole('textbox', { name: 'CVV' }).fill(CARD_CVV || '');
        await this.page.getByRole('button', { name: 'Pay ₹' }).click();  
    }
}