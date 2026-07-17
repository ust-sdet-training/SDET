import { Page } from "@playwright/test";
import { BookingLocators } from "../locators/BookingLocator";
import { logger } from "../logger/logger";

export class BookingPage {

    private readonly page: Page;
    private readonly locator: BookingLocators;

    constructor(page: Page) {
        this.page = page;
        this.locator = new BookingLocators(page);

    }

    async bookFirstFlight(){
        logger.info("Booking the first available flight");
        await this.locator.bookButton.first().click();
        logger.info("Flight booking action completed");
    }

}