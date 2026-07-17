import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export class SeatSelectionPage extends BasePage {

    readonly continueButton: Locator;

    constructor(page: Page) {
        super(page);
        this.continueButton = page.getByRole("button", {
            name: "Continue to passenger details"
        });

    }

    async selectAvailableSeat() {
        const availableSeat = this.page
            .locator('[aria-label*="available"]')
            .first();
        await availableSeat.click();
    }

    async selectSeat(seatNumber: string) {
        await this.page
            .getByLabel(new RegExp(`Seat\\s+${seatNumber}.*available`))
            .click();
    }

    async continueBooking() {
        await this.click(this.continueButton);
    }

}