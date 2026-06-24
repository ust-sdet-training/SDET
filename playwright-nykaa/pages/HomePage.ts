import { Page } from "@playwright/test";


export class HomePage{
    constructor(private readonly page: Page) {}

    async goto() {
        await this.page.goto('/', { waitUntil: "domcontentloaded"});
    }

    async searchProduct(query: string){
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).click();
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill(query);
        await this.page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
    }
}