import { expect, Page } from "@playwright/test";

export class ConfirmationPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async confirmSeat(): Promise<void> {
        await this.page.getByText('TS-1002-').click();
        await this.page.getByRole('button', { name: 'View my trips' }).click();
    }
}