import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";


export class PaymentPage extends BasePage {


    readonly cardHolderNameTextbox: Locator;
    readonly cardNumberTextbox: Locator;
    readonly expiryTextbox: Locator;
    readonly cvvTextbox: Locator;
    readonly payButton: Locator;

    constructor(page: Page) {

        super(page);
        this.cardHolderNameTextbox = page.getByLabel("Name on card");
        this.cardNumberTextbox = page.getByLabel("Card number");
        this.expiryTextbox = page.getByLabel("Expiry");
        this.cvvTextbox = page.getByLabel("CVV");
        this.payButton = page.getByRole("button", {name: "Pay"});

    }



    async enterPaymentDetails(payment: {cardHolderName: string; cardNumber: string; expiryDate: string; cvv: string;}) {
        logger.info("[PaymentPage] Entering payment details");
        await this.fill(this.cardHolderNameTextbox,payment.cardHolderName, "card holder name");
        await this.fill(this.cardNumberTextbox, payment.cardNumber, "card number");
        await this.fill(this.expiryTextbox,payment.expiryDate, "expiry date");
        await this.fill(this.cvvTextbox,payment.cvv, "CVV");

    }

    async completePayment() {
        logger.info("[PaymentPage] Submitting payment");
        await this.click(this.payButton, "Pay button");
    }

}