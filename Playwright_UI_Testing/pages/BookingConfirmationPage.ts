import { expect, Page } from "@playwright/test";
import { BookingConfirmationLocators } from "../locators/BookingConfirmationLocators";

export class BookingConfirmationPage {

    private locators: BookingConfirmationLocators;

    constructor(private page: Page) {
        this.locators = new BookingConfirmationLocators(page);
    }

    async verifyBookingConfirmationPage() {
        await expect(this.locators.pnr()).toBeVisible();
        await expect(this.locators.bookingStatus()).toBeVisible();
    }

    async getPNR() {
        return (await this.locators.pnr().textContent())?.trim();
    }

    async getBookingStatus() {
        return (await this.locators.bookingStatus().textContent())?.trim();
    }

    async getJourneyType() {
        return (await this.locators.journeyType().textContent())?.trim();
    }

    async getSeatNumber() {
        return (await this.locators.seatNumber().textContent())?.trim();
    }

    async getAmountPaid() {
        return (await this.locators.amountPaid().textContent())?.trim();
    }

    async clickViewMyTrips() {
        await this.locators.viewMyTripsButton().click();
    }

    async verifyPNRFormat() {
        await expect(this.locators.pnr()).toHaveText(/^TS-1026-\d{4}$/);
    }
}