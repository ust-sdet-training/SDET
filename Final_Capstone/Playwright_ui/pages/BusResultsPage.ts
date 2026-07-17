import { expect, Page } from "@playwright/test";
import { Logger } from "../src/logger/logger";

export class BusResultsPage {

    constructor(private page: Page) {}

    private acSeaterFilter = () =>
        this.page.getByRole('checkbox', {
            name: 'A/C Seater'
        });

    private orangeTours = () =>
        this.page.getByRole('article', {
            name: /Orange Tours/i
        });

    private selectSeatsButton = () =>
        this.page.getByRole('button', {
            name: 'Select Seats'
        });

    async filterACSeater() {

        Logger.info("Applying AC Seater Filter");

        await this.acSeaterFilter().check();

    }

    async verifyBusDisplayed() {

        await expect(this.orangeTours()).toBeVisible();

        Logger.success("Orange Tours Bus Displayed");

    }

    async selectBus() {

        Logger.info("Selecting Bus");

        await this.selectSeatsButton().click();

    }

}