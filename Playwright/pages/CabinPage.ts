import { Page } from "@playwright/test";
import { CabinLocators } from "../locators/CabinLocators";
import { logger } from "../logger/logger";

export class CabinPage {

    private readonly page: Page;
    private readonly locator: CabinLocators;

    constructor(page: Page) {
        this.page = page;
        this.locator = new CabinLocators(page);
    }

    async selectSeat() {
        logger.info("Selecting seat");
        await this.locator.seat.first().click();
    }

    async continueBooking() {
        logger.info("Clicking Continue");
        await this.locator.continue.click();
    }

    async selectSeatAndContinue(maxRetries = 3) {

        for (let attempt = 1; attempt <= maxRetries; attempt++) {

            logger.info(`Seat Selection Attempt : ${attempt}`);

            await this.selectSeat();
            await this.continueBooking();

            try {

                await this.locator.passengerFirstName.waitFor({
                    state: "visible",
                    timeout: 5000
                });

                logger.info("Passenger Details page loaded successfully");

                return;

            } catch {

                logger.warn("Seat Hold Expired");

                if (await this.locator.popupOkButton.isVisible().catch(() => false)) {
                    await this.locator.popupOkButton.click();
                }

                await this.page.waitForTimeout(1000);
            }
        }

        throw new Error("Seat Hold Expired after maximum retry attempts.");

    }

    async selectSeatWithRecovery(maxRetry = 3) {

    for (let i = 1; i <= maxRetry; i++) {

        logger.info(`Seat Selection Attempt ${i}`);

        await this.selectSeat();

        await this.continueBooking();

        try {

            await this.page
                .locator("input[id^='name-']")
                .waitFor({
                    timeout: 4000
                });

            logger.info("Passenger page loaded");

            return;

        } catch {

            logger.warn("Seat Hold Expired");

            await this.handleSeatExpiry();

        }

    }

    throw new Error("Seat Hold Expired after retries");

}

private async handleSeatExpiry() {

    if (
        await this.page
            .getByText(/Seat Hold Expired|Reservation Expired/i)
            .isVisible()
            .catch(() => false)
    ) {

        logger.warn("Closing Seat Expiry Popup");

        await this.page
            .getByRole("button", {
                name: /OK|Retry|Close/i
            })
            .click()
            .catch(() => {});

        await this.page.waitForTimeout(1000);
    }

}

}