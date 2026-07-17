import { Page } from "@playwright/test";

export class PaymentLocators {

    constructor(private page: Page) {}

    cardNameInput = () =>
        this.page.locator("#cardName");

    cardNumberInput = () =>
        this.page.locator("#cardNumber");

    expiryInput = () =>
        this.page.locator("#cardExpiry");

    cvvInput = () =>
        this.page.locator("#cardCvv");

    payButton = () =>
        this.page.getByRole("button", { name: /Pay/i });
}