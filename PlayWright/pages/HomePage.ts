import { expect, Page } from "@playwright/test";

export class HomePage {
    
    constructor(readonly page: Page){}

    private search = this.page.getByPlaceholder("Search on Nykaa");
    // private title = this.page.getByTitle(/Buy Cosmetic Products & Beauty Products/)
    private logo = this.page.getByRole("img", {name: "Nykaa Logo"});

    async searchExisting(prod: string) {
        await this.page.goto("/", {waitUntil: 'domcontentloaded'})
        await expect(this.page).toHaveTitle(/Buy Cosmetics Products & Beauty Products/)
        await expect(this.logo).toBeVisible()
        await this.search.fill(prod)
        await this.search.press("Enter")
    }

    // async searchNonExisting(prod: string) {
    //     await this.page.goto("/", {waitUntil: 'domcontentloaded'})
    //     await expect(this.page).toHaveTitle(/Buy Cosmetics Products & Beauty Products/)
    //     await expect(this.logo).toBeVisible()
    //     await this.search.fill(prod)
    //     await this.search.press("Enter")
    // }
}