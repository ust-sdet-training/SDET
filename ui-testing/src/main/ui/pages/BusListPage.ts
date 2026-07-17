import { expect, Page } from "@playwright/test";

export class BusListPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    
    async selectBus(busName: string): Promise<void> {
          await this.page.getByLabel(busName).getByRole('button', { name: 'Select Seats' }).click();
    }
}