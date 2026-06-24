import {expect, Page} from "@playwright/test";

export class HomePage {
    constructor(private readonly page:Page) {}

    async goto(){
        await this.page.goto("/");
    }

    async verifyPageTitle(){
        await expect(this.page).toHaveTitle("Buy Cosmetics Products & Beauty Products Online in India at Best Price | Nykaa");
        await expect(this.page).toHaveURL("https://www.nykaa.com/");
    }

    async positiveSearchProduct(){
        const searchInput = this.page.getByPlaceholder("Search on Nykaa");
        await searchInput.click();
        await searchInput.fill("Shirt");
        await searchInput.press("Enter");
        await expect(this.page.getByRole("heading",{level: 1})).toContainText("Shirt");
    }

    async negativeSearchProduct(){
        const searchInput = this.page.getByPlaceholder("Search on Nykaa");
        await searchInput.click();
        await searchInput.fill("abcd");
        await searchInput.press("Enter");
        await expect(this.page.getByText("No results found")).toBeVisible();
    }
}