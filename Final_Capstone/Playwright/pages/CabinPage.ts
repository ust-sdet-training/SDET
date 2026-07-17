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

    async selectSeat(){
        logger.info("Selecting a seat");
        await this.locator.seat.click();
    }

    async continueBooking(){
        logger.info("Continuing booking to passenger details");
        await this.locator.continue.click();
    }

    async selectSeatAndContinue(){
        logger.info("Starting seat selection");
        await this.selectSeat();
        await this.continueBooking();
        logger.info("Seat selection completed");
    }

}