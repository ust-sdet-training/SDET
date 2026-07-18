import { Locator, Page } from "@playwright/test";

export class CardLocators {

    readonly cardHolderName: Locator;

    readonly cardNumber: Locator;

    readonly expiry: Locator;

    readonly cvv: Locator;

    readonly payButton: Locator;

    constructor(page: Page) {

        this.cardHolderName = page.getByLabel("Name on card");

        this.cardNumber = page.getByLabel("Card number");

        this.expiry = page.getByLabel("Expiry");

        this.cvv = page.getByLabel("CVV");

        this.payButton = page.getByRole("button", {name: /Pay/i});

    }

}