import { Page } from "@playwright/test";
import { CheckoutLocators } from "../locators/CheckoutLocators";
import { expect } from "../fixtures/test";

export class CheckoutPage {

    private readonly locators: CheckoutLocators;

    constructor(private readonly page: Page) {
        this.locators = new CheckoutLocators(page);
    }

     async fillName(name:string): Promise<void> {
        await this.locators.cardName().fill(name);
    }

    async fillNumber(number:string): Promise<void> {
        await this.locators.cardNumber().fill(number);
    }

    async fillExpiry(expiry:string): Promise<void> {
        await this.locators.expiry().fill(expiry);
    }

    async fillCVV(cvv:string): Promise<void> {
        await this.locators.cvv().fill(cvv);
    }


    async clickPay(): Promise<void>{
        await this.locators.payButton().click();
    }

    async getBookingOutcome(): Promise<"Confirmed" | "Payment Declined"> {

    const declined = await this.locators.paymentDeclined().isVisible().catch(() => false);

    if (declined) {
        return "Payment Declined";
    }

    return "Confirmed";
}

}