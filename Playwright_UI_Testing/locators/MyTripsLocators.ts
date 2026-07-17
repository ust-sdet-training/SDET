import { Page } from "@playwright/test";

export class MyTripsLocators {

    constructor(private page: Page) {}

    pnr = () =>
    this.page.locator(".title[data-id='pnr']").first();

    bookingStatus = () =>
        this.page.locator("[data-id='state']").first();

    amountPaid = () =>
        this.page.locator(".fare");

    cancelButton = () =>
        this.page.getByRole("button", { name: "Cancel" });
}

