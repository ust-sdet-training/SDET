import { Locator, Page } from "@playwright/test";

export class MyTripsLocators {

    readonly pageTitle: Locator;

    readonly tripsList: Locator;

    readonly tripCards: Locator;

    readonly pnrList: Locator;

    readonly statusList: Locator;

    readonly cancelButtons: Locator;

    constructor(page: Page) {

        this.pageTitle =
            page.getByRole("heading", { name: "My Trips" });

        this.tripsList =
            page.locator("[data-id='trips-list']");

        this.tripCards =
            page.locator("[data-id^='trip-']");

        this.pnrList =
            page.locator("[data-id='pnr']");

        this.statusList =
            page.locator("[data-id='state']");

        this.cancelButtons =
            page.locator(".cancel-form button");

    }

}