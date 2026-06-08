import type{ Page } from "@playwright/test";

export class ProductPage{
    constructor(private readonly page: Page){}
    private addButton= ()=> this.page.getByRole("button", {name: "Add to cart"});

    
    async addProduct(){
        const apiAdd= this.page.waitForResponse(
            r=> r.url().includes('/api/cart') &&
            r.status() ===200
        )

        await this.addButton().click()
        await apiAdd;
    }

    results(){
        return this.page.getByTestId("cart-count");
    }
}