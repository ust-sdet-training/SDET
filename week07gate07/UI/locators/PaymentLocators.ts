import { Locator, Page } from "@playwright/test";

export class PaymentLocators {

    readonly paymentForm: Locator;

    readonly bookingId: Locator;

    readonly cardName: Locator;
    readonly cardNumber: Locator;
    readonly cardExpiry: Locator;
    readonly cardCvv: Locator;

    readonly payButton: Locator;

    readonly orderSummary: Locator;

    readonly journeyType: Locator;
    readonly seatList: Locator;
    readonly amount: Locator;

    readonly offerStrip: Locator;
    readonly offers: Locator;

    constructor(private page: Page) {

        // Main payment form
        this.paymentForm =
            page.locator("[data-id='payment-form']");

        // Booking information
        this.bookingId =
            page.locator("[data-id='booking-id']");

        // Card details
        this.cardName =
            page.locator("#cardName");

        this.cardNumber =
            page.locator("#cardNumber");

        this.cardExpiry =
            page.locator("#cardExpiry");

        this.cardCvv =
            page.locator("#cardCvv");

        // Payment button
        this.payButton =
            page.locator("button[type='submit']");

        // Fare summary
        this.orderSummary =
            page.locator("[data-id='order-summary']");

        this.journeyType =
            page.locator("[data-id='journey-type']");

        this.seatList =
            page.locator("[data-id='seat-list']");

        this.amount =
            page.locator("[data-id='amount']");

        // Promotional offers
        this.offerStrip =
            page.locator(".offer-strip");

        this.offers =
            page.locator(".offer");
    }
}