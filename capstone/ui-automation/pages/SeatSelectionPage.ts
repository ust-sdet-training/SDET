import { Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";

export class SeatSelectionPage extends BasePage {

    readonly continueButton: Locator;

    constructor(page: Page) {
        super(page);
        this.continueButton = page.getByRole("button", {
            name: "Continue to passenger details"
        });

    }

    async selectAvailableSeat() {
        logger.info("[SeatSelectionPage] Selecting first available seat");
        const availableSeat = this.page
            .locator('[aria-label*="available"]')
            .first();
        await availableSeat.click();
    }

    async selectSeat(seatNumber: string) {
        logger.info(`[SeatSelectionPage] Selecting seat ${seatNumber}`);
        await this.page
            .getByLabel(new RegExp(`Seat\\s+${seatNumber}.*available`))
            .click();
    }

    async continueBooking() {
        logger.info("[SeatSelectionPage] Continuing to passenger details");
        await this.click(this.continueButton, "Continue button");
    }

}