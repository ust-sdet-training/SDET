import type { Page } from "@playwright/test";

export class CartPage{
    constructor(private readonly page: Page){}
    private toCheckoutButton= ()=> this.page.getByRole("button", {name: "Proceed to checkout"});

    async open(){
        await this.page.goto('/cart');
    }

    async productPresent(){
        return this.page.getByTestId('cart-count');
    }

    async proceedToCheckout(){
        const apiCheckout= this.page.waitForResponse(
            r=> r.url().includes('/api/cart') &&
            r.status() ===200
        )
        
        await this.toCheckoutButton().click()
        await apiCheckout;
    }

    results(){
        return this.page.getByTestId("cart-count");
    }
}