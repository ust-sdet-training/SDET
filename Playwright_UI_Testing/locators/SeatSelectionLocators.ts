import { Page } from "@playwright/test";

export class SeatSelectionLocators {

    constructor(private page: Page) {}

    lowerDeckButton = () =>
        this.page.locator("button[data-target='lower']");

    upperDeckButton = () =>
        this.page.locator("button[data-target='upper']");

    availableSeats = () =>
        this.page.locator("[data-state='available']");

    selectedSeats = () =>
        this.page.locator("[data-state='selected']");

    bookedSeats = () =>
        this.page.locator("[data-state='booked']");

    seatByNumber = (seatNo: string) =>
        this.page.getByText(seatNo, { exact: true });

    continueButton = () =>
        this.page.locator("#continue-btn");
}