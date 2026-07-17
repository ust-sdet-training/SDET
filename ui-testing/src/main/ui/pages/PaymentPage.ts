import { expect, Page } from "@playwright/test";

export class PaymentPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async payment(): Promise<void> {
        await this.page.getByRole('textbox', { name: 'Name on card' }).click();
        await this.page.getByRole('textbox', { name: 'Name on card' }).fill('Bob Tedd');
        await this.page.getByRole('textbox', { name: 'Card number' }).click();
        await this.page.getByRole('textbox', { name: 'Card number' }).fill('1234567890123456');
        await this.page.getByRole('textbox', { name: 'Expiry' }).click();
        await this.page.getByRole('textbox', { name: 'Expiry' }).fill('12/28');
        await this.page.getByRole('textbox', { name: 'CVV' }).click();
        await this.page.getByRole('textbox', { name: 'CVV' }).fill('244');
        await this.page.getByRole('button', { name: 'Pay ₹' }).click();  
    }
}