import { expect, Page } from "@playwright/test";

export class HomePage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
        //await expect(this.page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    }

    resultCount = () => this.page.getByTestId('catalog-result-count');
    
    async search(from: string, to: string, date: string): Promise<void> {
        await this.page.getByRole('tab', { name: 'Buses' }).click();
        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('combobox', { name: 'From' }).fill(from);
        await this.page.getByRole('option', { name: 'Mumbai BOM' }).click();
        await this.page.getByRole('combobox', { name: 'To' }).click();
        await this.page.getByRole('combobox', { name: 'To' }).fill(to);
        await this.page.getByRole('option', { name: 'Delhi DEL' }).click();
        await this.page.getByRole('textbox', { name: 'Date' }).fill(date);

        await this.page.getByRole('button', { name: 'Search' }).click();
    }
}