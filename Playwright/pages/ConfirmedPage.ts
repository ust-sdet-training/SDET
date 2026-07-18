import { expect, Page } from "@playwright/test";
import { ConfirmedLocators } from "../locators/ConfirmedLocator";
import { logger } from "../logger/logger";

export class ConfirmedPage {

    private readonly booking: ConfirmedLocators;

    constructor(page: Page) {

        this.booking = new ConfirmedLocators(page);

    }

    async verifyBookingConfirmed(){
        logger.info("Verifying booking confirmation page");
        
        await this.booking.bookingMessage.waitFor({
        state: "visible",
        timeout: 15000
         });

        await expect(this.booking.bookingMessage).toBeVisible();

        await expect(this.booking.bookingStatus).toHaveText('CONFIRMED');

        logger.info("Booking confirmation page verified");
    }

    async getBookingReference(){
        const bookingReference = (await this.booking.bookingReference.textContent());
        logger.info(`Retrieved booking reference: ${bookingReference}`);

        return bookingReference;
    }

    async getAmountPaid(){
        const amountPaid = (
        await this.booking.amountPaid.textContent());
        logger.info(`Retrieved amount paid: ${amountPaid}`);
        return amountPaid;
    }

    async clickViewMyTrips() {
        logger.info("Opening My Trips view");
        await this.booking.viewMyTripsButton.click();
    }

}