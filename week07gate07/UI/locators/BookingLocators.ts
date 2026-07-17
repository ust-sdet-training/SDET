import { Locator, Page } from "@playwright/test";

export class BookingLocators {

    readonly pnrBlock: Locator;

    readonly pnr: Locator;

    readonly bookingState: Locator;

    readonly journeyType: Locator;

    readonly seatList: Locator;

    readonly amount: Locator;

    readonly myTripsButton: Locator;

    readonly supportPanel: Locator;

    constructor(private page: Page) {

        // Ticket

        this.pnrBlock =
            page.locator("[data-id='pnr-block']");

        this.pnr =
            page.locator("[data-id='pnr']");

        this.bookingState =
            page.locator("[data-id='state']");

        // Summary

        this.journeyType =
            page.locator("[data-id='journey-type']");

        this.seatList =
            page.locator("[data-id='seat-list']");

        this.amount =
            page.locator("[data-id='amount']");

        // Navigation

       this.myTripsButton =
    page.getByRole("button", {
        name: "View my trips"
    });

        this.supportPanel =
            page.locator(".summary-rail");
    }

}