import { Locator, Page } from "@playwright/test";

export class CheckoutLocators {

    constructor(private readonly page: Page) {}

    verifyPage(): Locator {
        return this.page.getByRole("heading", {name: "Secure checkout"});
    }

    cardName(): Locator {
        return this.page.getByLabel("Name on card");
    }

    cardNumber(): Locator{
        return this.page.getByLabel("Card number");
    }

    expiry(): Locator{
        return this.page.getByLabel("Expiry");
    }

    cvv(): Locator{
        return this.page.getByLabel("CVV");
    }

    payButton(): Locator{
        return this.page.getByRole("button", {name: "Pay ₹1298.85"})
    }

    paymentDeclined(): Locator{
        return this.page.locator('[data-ref="payment-error"]');
    }
    

}