import { expect, Page } from "@playwright/test";

export class BookingPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async travellerDetails(email: string, phone: string): Promise<void> {
        await this.page.getByRole('textbox', { name: 'First name (seat L3)' }).click();
        await this.page.getByRole('textbox', { name: 'First name (seat L3)' }).fill('Bob');
        await this.page.getByRole('textbox', { name: 'Last name (seat L3)' }).click();
        await this.page.getByRole('textbox', { name: 'Last name (seat L3)' }).fill('tedd');
        await this.page.getByRole('spinbutton', { name: 'Age (seat L3)' }).click();
        await this.page.getByRole('spinbutton', { name: 'Age (seat L3)' }).fill('23');
        await this.page.getByLabel('Gender (seat L3)').selectOption('male');
        await this.page.getByRole('textbox', { name: 'Email' }).click();
        await this.page.getByRole('textbox', { name: 'Email' }).fill(email);
        await this.page.getByRole('textbox', { name: 'Phone number' }).click();
        await this.page.getByRole('textbox', { name: 'Phone number' }).fill(phone);
        await this.page.getByRole('button', { name: 'Continue to payment' }).click();
    }
}