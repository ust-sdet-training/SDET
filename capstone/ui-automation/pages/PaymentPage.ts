import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";


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
        await this.fill(this.cardHolderNameTextbox,payment.cardHolderName);
        await this.fill(this.cardNumberTextbox, payment.cardNumber);
        await this.fill(this.expiryTextbox,payment.expiryDate);
        await this.fill(this.cvvTextbox,payment.cvv);

    }

    async completePayment() {
        await this.click(this.payButton);
    }

}