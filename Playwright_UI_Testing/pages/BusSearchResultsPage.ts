import { expect, Page } from "@playwright/test";
import { BusSearchResultsLocators } from "../locators/BusSearchResultsLocators";

export class BusSearchResultsPage {

    private locators: BusSearchResultsLocators;

    constructor(private page: Page) {
        this.locators = new BusSearchResultsLocators(page);
    }

    async verifyResultsLoaded() {
        await expect(this.locators.results()).toBeVisible();
        await expect(this.locators.firstBusCard()).toBeVisible();
    }

    async getOperatorName() {
        return (await this.locators.operatorName().textContent())?.trim();
    }

    async getBusType() {
        return (await this.locators.busType().textContent())?.trim();
    }

    async getFare() {
        return (await this.locators.fare().textContent())?.trim();
    }

    async clickSelectSeats() {
        await this.locators.selectSeatsButton().click();
    }

    async selectFirstBus() {
        await this.verifyResultsLoaded();
        await this.clickSelectSeats();
    }
}