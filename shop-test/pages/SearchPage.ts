import { expect, type Page } from "@playwright/test";

export class SearchPage{
    constructor(private readonly page: Page){}

    async open(){
        await this.page.goto('/catalog');
    }

    async search(searchKey:string){
        const searchRes= this.page.waitForResponse(
            r=> r.url().includes('/api/products') && r.status()===200
        )
        await this.page.getByRole("searchbox", {name:"Search products"}).fill(searchKey);
        await this.page.getByRole("button", {name: "Search"}).click();
        await searchRes;
    }

    results(){
        return this.page.getByTestId('product-card');
    }

    async viewFirstProd(){
        await expect(this.page.locator(".product-grid").first()).toBeVisible();
        await this.page.locator(".product-grid").first().getByRole("link").click();
    }


}