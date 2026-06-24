import { expect, Page } from "@playwright/test";

export class ProductsPage {
    constructor(readonly page: Page){}

    async esistingProducts(prod: string) {
        await expect(this.page.locator(".page-title-search")).toHaveText(prod)
        await expect(this.page).toHaveURL(/q=Cosmetics/i)
    }

    async nonExistingProducts(prod: string) {
        await expect(this.page.getByText(`No results found for '${prod}'.`)).toBeVisible()
    }
}