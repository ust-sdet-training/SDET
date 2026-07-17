import { expect, Page } from "@playwright/test";

export class BusDetailPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async selectSeat(): Promise<void> {
        await this.page.getByRole('button', { name: 'Seat L3 available' }).click();
        await this.page.getByRole('button', { name: 'Continue to passenger details'}).click();    
    }
}