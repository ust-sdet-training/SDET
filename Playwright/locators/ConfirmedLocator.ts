import { Locator, Page } from "@playwright/test";

export class ConfirmedLocators {

    readonly bookingMessage: Locator;

    readonly bookingReference: Locator;

    readonly bookingStatus: Locator;

    readonly amountPaid: Locator;

    readonly viewMyTripsButton: Locator;

    constructor(page: Page) {

        this.bookingMessage = page.getByRole("heading", {name: /You're all set!/i});

        this.bookingReference = page.locator(".pnr");

        this.bookingStatus = page.getByText("CONFIRMED",{exact:true});

        this.amountPaid = page.getByText(/₹/);

        this.viewMyTripsButton = page.getByRole("button", {name: /View my trips/i});

    }

}