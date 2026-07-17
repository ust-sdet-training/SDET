import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class ConfirmationPage extends BasePage {
    readonly confirmationHeading : Locator
    readonly bookingStatus : Locator
    readonly pnr: Locator
    readonly viewMyTripsButton : Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log);

        this.confirmationHeading  = page.getByRole("heading", {name: "You're all set! 🎉"});
        this.bookingStatus  = page.locator("[data-id='state']");
        this.pnr = page.locator("[data-id='pnr']");
        this.viewMyTripsButton = page.getByRole("button", {name: "View my trips"})
    }

    async verifyBookingConfirmed(): Promise<void> {
        this.log.info("Verifying booking confirmation page");
        await this.isVisible(this.confirmationHeading)
        await expect(this.bookingStatus).toHaveText("CONFIRMED");
        await expect(this.pnr).not.toHaveText("");
        await this.isVisible(this.viewMyTripsButton)
    }
}