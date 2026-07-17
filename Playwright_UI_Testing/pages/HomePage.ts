import { expect, Page } from "@playwright/test";
import { HomeLocators } from "../locators/HomeLocators";

export class HomePage {

    private locators: HomeLocators;

    constructor(private page: Page) {
        this.locators = new HomeLocators(page);
    }

    async verifyHomePage() {

        // Switch from Flights to Buses
        await this.locators.busesTab().click();

        await expect(this.locators.fromInput()).toBeVisible();

        await expect(this.locators.toInput()).toBeVisible();
        await expect(this.locators.dateInput()).toBeVisible();
        await expect(this.locators.searchButton()).toBeVisible();
    }

    async selectFromCity(city: string) {

        await this.locators.fromInput().click();
        await this.locators.fromInput().fill(city);

        await this.page
            .getByRole("option")
            .first()
            .click();
    }

    async selectToCity(city: string) {

        await this.locators.toInput().click();
        await this.locators.toInput().fill(city);

        await this.page
            .getByRole("option")
            .first()
            .click();
    }

    async selectJourneyDate(date: string) {
        await this.locators.dateInput().fill(date);
    }

    async swapLocations() {
        await this.locators.swapButton().click();
    }

    async clickSearch() {
        await this.locators.searchButton().click();
    }

    async searchBus(from: string, to: string, date: string) {

        await this.verifyHomePage();

        await this.selectFromCity(from);
        await this.selectToCity(to);
        await this.selectJourneyDate(date);

        await this.clickSearch();
    }
}