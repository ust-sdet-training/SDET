import { Page } from "@playwright/test";

export class SeatSelectionLocators {

    constructor(private page: Page) {}

    availableSeats = () =>
        this.page.locator("[data-state='available']");

    selectedSeats = () =>
        this.page.locator("[data-state='selected']");

    bookedSeats = () =>
        this.page.locator("[data-state='booked']");

    seatByNumber = (seatNo: string) =>
        this.page.getByText(seatNo);

    continueButton = () =>
        this.page.locator("#continue-btn");
}