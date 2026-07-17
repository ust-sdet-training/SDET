import { expect, Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { BookingLocators } from "../locators/BookingLocators";
import { BookingData } from "../test-data/BookingData";

export class BookingPage extends BasePage {

    readonly locator: BookingLocators;

    constructor(page: Page) {

        super(page);

        this.locator = new BookingLocators(page);

    }

    async verifyBookingSuccess() {

        await expect(this.locator.pnrBlock)
            .toBeVisible();

        await expect(this.locator.bookingState)
            .toHaveText("CONFIRMED");

    }

    async getPNR(): Promise<string> {

        return (
            await this.locator.pnr.textContent()
        ) ?? "";

    }

    async getBookingStatus(): Promise<string> {

        return (
            await this.locator.bookingState.textContent()
        ) ?? "";

    }

    async getSeat(): Promise<string> {

        return (
            await this.locator.seatList.textContent()
        ) ?? "";

    }

    async getAmount(): Promise<string> {

        return (
            await this.locator.amount.textContent()
        ) ?? "";

    }

    async verifyPNRPrefix(prefix: string) {

        const pnr = await this.getPNR();

        expect(pnr.startsWith(prefix)).toBeTruthy();

    }

    async openMyTrips() {

        await this.locator.myTripsButton.click();

        await this.page.waitForLoadState("networkidle");

    }

    // ===========================================
    // NEW
    // ===========================================

    async goHome() {

        await this.page.goto(BookingData.BASE_URL);

        await this.page.waitForLoadState("networkidle");

    }

}