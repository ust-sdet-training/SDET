import { Page, Locator } from "@playwright/test";

export class CabinLocators {

    readonly continue: Locator;
    readonly seat: Locator;

    readonly passengerFirstName: Locator;

    readonly seatExpiredPopup: Locator;

    readonly popupOkButton: Locator;

    constructor(page: Page) {

        this.continue = page.locator("#continue-btn");

        this.seat = page.locator(".seat.available").first();

        this.passengerFirstName = page.locator("input[id^='name-']");

        this.seatExpiredPopup = page.getByText(/Seat Hold Expired|Reservation Expired/i);

        this.popupOkButton = page.getByRole("button", {
            name: /OK|Close|Retry/i
        });

    }

}