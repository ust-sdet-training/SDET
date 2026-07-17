import { expect, Page } from "@playwright/test";
import { Logger } from "../src/logger/logger";

export class BusSearchPage {

    constructor(private page: Page) {}

    private busesTab = () =>
        this.page.getByRole('link', { name: 'Buses' });

    private heading = () =>
        this.page.getByRole('heading', {
            name: 'Book bus tickets across India'
        });

    private from = () =>
        this.page.getByRole('combobox', { name: 'From' });

    private to = () =>
        this.page.getByRole('combobox', { name: 'To' });

    private date = () =>
        this.page.getByRole('textbox', {
            name: 'Date of journey'
        });

    private searchButton = () =>
        this.page.getByRole('button', {
            name: 'Search buses'
        });

    async openBusPage() {

        Logger.info("Opening Bus Module");

        await this.busesTab().click();

        await expect(this.heading()).toBeVisible();

    }

    async selectFrom(city: string) {

        Logger.info(`Selecting Source : ${city}`);

        await this.from().click();

        await this.page
            .getByRole('option', { name: city })
            .click();

    }

    async selectTo(city: string) {

        Logger.info(`Selecting Destination : ${city}`);

        await this.to().click();

        await this.page
            .getByRole('option', { name: city })
            .click();

    }

    async selectJourneyDate(date: string) {

        Logger.info(`Journey Date : ${date}`);

        await this.date().fill(date);

    }

    async searchBus() {

        Logger.info("Searching Bus");

        await this.searchButton().click();

    }

}