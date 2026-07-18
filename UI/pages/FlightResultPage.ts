import { expect, Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { FlightResultLocators } from "../locators/FlightResultLocators";

export class FlightResultPage extends BasePage {

    readonly locator: FlightResultLocators;

    constructor(page: Page) {
        super(page);
        this.locator = new FlightResultLocators(page);
    }

    async verifyFlightResultLoaded() {

        await expect(this.locator.resultCards.first()).toBeVisible();

    }

    async verifyResultsAvailable() {

        const count = await this.locator.resultCards.count();

        expect(count).toBeGreaterThan(0);

    }

    async sortByPrice() {

        await this.locator.sortPrice.click();

    }

    async sortByRating() {

        await this.locator.sortRating.click();

    }

    async sortByDeparture() {

        await this.locator.sortDeparture.click();

    }

    async chooseFirstFlight() {

        await this.locator.firstBookButton.click();

        await this.page.waitForLoadState("networkidle");

    }

    async chooseFlightByIndex(index: number) {

        await this.page
            .locator(".trip-card .btn.btn-cta")
            .nth(index)
            .click();

        await this.page.waitForLoadState("networkidle");

    }

    async chooseFlightByAirline(airline: string) {

        const card = this.page.locator(".trip-card")
            .filter({
                hasText: airline
            });

        await card.locator(".btn.btn-cta").click();

        await this.page.waitForLoadState("networkidle");

    }

    async applyAirlineFilter(name: string) {

        await this.page
            .locator(`.f-airline[value="${name}"]`)
            .check();

    }

    async applyDepartureFilter(value: string) {

        await this.page
            .locator(`.f-dep[value="${value}"]`)
            .check();

    }

    async movePriceSlider(value: number) {

        await this.locator.priceSlider.fill(value.toString());

    }

}