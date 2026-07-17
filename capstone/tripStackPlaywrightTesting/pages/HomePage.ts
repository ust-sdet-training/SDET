import { expect, Locator, Page } from '@playwright/test';

export class HomePage {

    readonly page: Page;

    readonly homeHeading: Locator;
    readonly fromTextbox: Locator;
    readonly toTextbox: Locator;
    readonly goaOption: Locator;
    readonly dateTextbox: Locator;
    readonly searchButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.homeHeading = page.getByRole('heading', {
            name: 'Book flights & buses across'
        });

        this.fromTextbox = page.getByRole('combobox', {
            name: 'From'
        });

        this.toTextbox = page.getByRole('combobox', {
            name: 'To'
        });

        this.goaOption = page.getByRole('option', {
            name: 'Goa GOI'
        });

        this.dateTextbox = page.getByRole('textbox', {
            name: 'Date'
        });

        this.searchButton = page.getByRole('button', {
            name: 'Search'
        });

    }

    async verifyHomePageLoaded() {

        await expect(this.homeHeading).toBeVisible();

    }

    async enterSource(source: string) {

        await this.fromTextbox.fill(source);

    }

    async enterDestination(destination: string) {

        await this.toTextbox.fill(destination);

        await this.goaOption.click();

    }

    async selectJourneyDate(date: string) {

        await this.dateTextbox.fill(date);

    }

    async clickSearch() {

        await this.searchButton.click();

    }

}