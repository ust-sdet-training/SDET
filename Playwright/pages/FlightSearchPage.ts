import { expect, Page } from "@playwright/test";
import { FlightSearchLocators } from "../locators/FlightSearchLocators";
import { logger } from "../logger/logger";

export class FlightSearchPage {

    private readonly page: Page;
    private readonly locator: FlightSearchLocators;

    constructor(page: Page) {

        this.page = page;
        this.locator = new FlightSearchLocators(page);

    }

    async selectSource(city: string) {
        logger.info(`Selecting source city: ${city}`);
        await expect(this.locator.fromDropdown).toBeVisible({timeout: 30000});

        await this.locator.fromDropdown.click();
        await this.page.getByRole("option", { name: city }).click();
    }

    async selectDestination(city: string){
        logger.info(`Selecting destination city: ${city}`);
        await this.locator.toDropdown.click();
        await this.page.getByRole("option", { name: city }).click();
    }

    async selectJourneyDate(date: string){
        logger.info(`Selecting journey date: ${date}`);
        await this.locator.dateTextbox.fill(date);
    }

    async clickSearch(){
        logger.info("Clicking search button");
        await this.locator.searchButton.click();
    }

    async searchFlight(source: string,destination: string,date: string){
        logger.info("Starting flight search flow");
        await this.selectSource(source);
        await this.selectDestination(destination);
        await this.selectJourneyDate(date);
        await this.clickSearch();
        logger.info("Flight search flow completed");
    }

}