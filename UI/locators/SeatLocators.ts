import { Locator, Page } from "@playwright/test";

export class SeatLocators {

    readonly seatMap: Locator;

    readonly availableSeats: Locator;

    readonly occupiedSeats: Locator;

    readonly selectedSeat: Locator;

    readonly continueButton: Locator;

    readonly selectedSeatLabel: Locator;

    readonly seatHiddenField: Locator;

    constructor(private page: Page) {

        this.seatMap =
            page.locator("[data-layout='cabin']");

        this.availableSeats =
            page.locator(".seat.available");

        this.occupiedSeats =
            page.locator(".seat.is-occupied");

        this.selectedSeat =
            page.locator(".seat.selected");

        this.continueButton =
            page.locator("#continue-btn");

        this.selectedSeatLabel =
            page.locator("#sel-readout");

        this.seatHiddenField =
            page.locator("#seat-ids");

    }

}