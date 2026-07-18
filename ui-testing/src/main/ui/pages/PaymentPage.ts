import { expect, Page } from "@playwright/test";

export class PaymentPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
    }

    
    async payment(CARD_NUMBER: string, CARD_EXPIRY: string, CARD_CVV: string): Promise<boolean> {
        await this.page.getByRole('textbox', { name: 'Name on card' }).fill('Bob Tedd');
        await this.page.getByRole('textbox', { name: 'Card number' }).fill(CARD_NUMBER);
        await this.page.getByRole('textbox', { name: 'Expiry' }).fill(CARD_EXPIRY || '');
        await this.page.getByRole('textbox', { name: 'CVV' }).fill(CARD_CVV || '');
        await this.page.getByRole('button', { name: 'Pay ₹' }).click();
        return !(await this.page.getByRole("alert").isVisible());
    }
}