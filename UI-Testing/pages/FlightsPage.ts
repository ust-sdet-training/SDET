import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class FlightsPage extends BasePage{
    readonly flightCards: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log)

        this.flightCards = page.locator("#results .flight-card");
    } 

    async verifyFlightResultsDisplayed(): Promise<void> {
        this.log.info("Verifying flight results are displayed");
        const count = await this.flightCards.count();
        this.log.info(`Number of flight cards: ${count}`);
        await expect(count).toBeGreaterThan(0);
    }

    async clickBookBtn(): Promise<void> {
        this.log.info("Clicking Book button")
        const firstFlight = this.flightCards.first()
        await this.click(firstFlight.getByRole("button", {name: "Book"}))
    }
}