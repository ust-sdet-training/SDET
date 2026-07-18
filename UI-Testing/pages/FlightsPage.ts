import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class FlightsPage extends BasePage{
    readonly flightCards: Locator
    // readonly firstFlightBookLink: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log)

        this.flightCards = page.locator("[aria-label='Flight results']");
        
    } 

    async verifyFlightResultsDisplayed(): Promise<void> {
        this.log.info("Verifying flight results are displayed");
        const count = await this.flightCards.count();
        this.log.info(`Number of flight cards: ${count}`);
        await expect(count).toBeGreaterThan(0);
    }

    async clickBookBtn(): Promise<void> {
        this.log.info("Clicking Book button")
        // await expect(this.firstFlightBookLink).toBeVisible();
        const firstFlight = this.flightCards.first()
        // console.log(firstFlight.locator("button").toString())
        // await this.click(firstFlight.getByRole("button", {name: "Book"}))
        await this.page.getByRole("button", { name: "Book" })
    .first()
    .click();
    }
}
