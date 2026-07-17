import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";
import { CardDetails } from "../model/CardDetails";

export class CheckoutPage extends BasePage {
    readonly checkoutText: Locator
    readonly cardName: Locator
    readonly cardNumber: Locator
    readonly cardExpiry: Locator
    readonly cardCvv: Locator
    readonly payButton: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log);

        this.checkoutText = page.getByRole("heading", { name: "Secure checkout" })
        this.cardName = page.getByLabel("Name on card")
        this.cardNumber = page.getByLabel("Card number")
        this.cardExpiry = page.getByLabel("Expiry")
        this.cardCvv = page.getByLabel("CVV")
        this.payButton = page.getByRole("button", {name: /^Pay/});
    }

    async verifyCheckoutPage(): Promise<void> {
        await expect(this.isVisible(this.checkoutText))
    }

    async fillCardDetails(card: CardDetails): Promise<void> {
        this.log.info("Entering payment details");
        await this.fill(this.cardName, card.name)
        await this.fill(this.cardNumber, card.cardNumber)
        await this.fill(this.cardExpiry, card.expiry)
        await this.fill(this.cardCvv, card.cvv)
    }

    async clickPay(): Promise<void> {
        this.log.info("Clicking Pay button");
        await this.click(this.payButton);
    }
}