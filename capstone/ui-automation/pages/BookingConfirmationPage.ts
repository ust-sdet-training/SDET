import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";


export class BookingConfirmationPage extends BasePage {

    readonly pnrNumber: Locator;
    readonly viewMyTripButton: Locator;


    constructor(page: Page) {
        super(page);

        this.pnrNumber = page.locator(".pnr");
        this.viewMyTripButton = page.getByRole("button", {
            name: "View My Trip"
        });
    }

    async getPNRNumber() {
        logger.info("[BookingConfirmationPage] Reading booking reference number");
        return await this.pnrNumber.textContent();
    }

    async clickViewMyTrip() {
        logger.info("[BookingConfirmationPage] Opening My Trips page");
        await this.viewMyTripButton.click();
        await expect(this.page).toHaveURL(/\/my-trips/);

    }

}