import type { Page } from "@playwright/test";

export class CheckoutPage{
    constructor(private readonly page: Page){}
    private placeOrderButton= ()=> this.page.getByRole("button", {name: "Place order"});
    private viewOrderButton= ()=> this.page.getByRole("button", {name: "View orders"});

    async open(){
        await this.page.goto('/checkout');
    }

    async placeOrder(){
        // const apiCheckout= await this.page.waitForResponse(
        //     r=> r.url().includes('/api/orders') &&
        //     r.status() ===200
        // ) 
        await this.placeOrderButton().click()
        // await apiCheckout;
    }

    async vieweOrder(){
        const apiCheckout= this.page.waitForResponse(
            r=> r.url().includes('/api/orders') &&
            r.status() ===200
        ) 
        await this.viewOrderButton().click()
        await apiCheckout;
    }

    results(){
        return this.page.getByTestId("cart-count");
    }

    async orderID(){
        const [text]=await this.page.locator(".confirmation-panel > p >strong").allTextContents()
        return text;
    }

}


