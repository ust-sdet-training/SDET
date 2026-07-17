import { Page } from "@playwright/test";

export class BookingConfirmationLocators {

    constructor(private page: Page) {}

    pnr = () =>
        this.page.locator("[data-id='pnr']");

    bookingStatus = () =>
        this.page.locator("[data-id='state']");

    journeyType = () =>
        this.page.locator("[data-id='journey-type']");

    seatNumber = () =>
        this.page.locator("[data-id='seat-list']");

    amountPaid = () =>
        this.page.locator("[data-id='amount']");

    viewMyTripsButton = () =>
        this.page.getByRole("button", { name: "View my trips" });
}