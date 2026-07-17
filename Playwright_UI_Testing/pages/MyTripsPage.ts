import { expect, Page } from "@playwright/test";
import { MyTripsLocators } from "../locators/MyTripsLocators";

export class MyTripsPage {

    private locators: MyTripsLocators;

    constructor(private page: Page) {
        this.locators = new MyTripsLocators(page);
    }

    async verifyMyTripsPage() {
        await expect(this.locators.pnr()).toBeVisible();
        await expect(this.locators.bookingStatus()).toBeVisible();
    }

    async getPNR() {
        return (await this.locators.pnr().textContent())?.trim();
    }

    async getBookingStatus() {
        return (await this.locators.bookingStatus().textContent())?.trim();
    }

    async getAmountPaid() {
        return (await this.locators.amountPaid().textContent())?.trim();
    }

    async verifyPNR(expectedPNR: string) {
        await expect(this.locators.pnr()).toHaveText(expectedPNR);
    }

    async verifyBookingStatus(expectedStatus: string) {
        await expect(this.locators.bookingStatus()).toHaveText(expectedStatus);
    }

    async cancelBooking() {
        await this.locators.cancelButton().click();
    }
}