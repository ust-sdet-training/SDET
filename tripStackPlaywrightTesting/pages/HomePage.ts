import { expect, Locator, Page } from '@playwright/test';

export class HomePage {

    readonly page: Page;

    readonly homeHeading: Locator;
    readonly fromTextbox: Locator;
    readonly toTextbox: Locator;
    readonly goaOption: Locator;
    readonly searchButton: Locator;
    readonly dateTextbox: Locator;

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

        this.searchButton = page.getByRole('button', {
            name: 'Search'
        });

        this.dateTextbox = page.getByRole('textbox', {
            name: 'Date'
        });

    }

    async verifyHomePageLoaded() {

        await expect(this.homeHeading).toBeVisible();

    }

    async enterSource(source: string) {

        await this.fromTextbox.click();
        await this.fromTextbox.fill(source);

    }

    async enterDestination(destination: string) {

        await this.toTextbox.click();
        await this.toTextbox.fill(destination);

        const option = this.page
            .getByRole('option')
            .filter({
                hasText: destination
            })
            .first();

        if (await option.count() > 0) {
            await option.click();
        }

    }

    async selectJourneyDate(date: string) {

        await this.dateTextbox.fill(date);

    }

    async selectReturnDate(afterDays: number) {

        const returnDate = new Date();

        returnDate.setDate(returnDate.getDate() + afterDays);

        const formattedDate = returnDate
            .toISOString()
            .split('T')[0];

        await this.dateTextbox.fill(formattedDate);

    }

    async clickSearch() {

        await this.searchButton.click();

    }

    async searchFlight(
        source: string,
        destination: string,
        date?: string
    ) {

        await this.verifyHomePageLoaded();

        await this.enterSource(source);

        await this.enterDestination(destination);

        if (date) {
            await this.selectJourneyDate(date);
        }

        await this.clickSearch();

    }

    async selectToday() {

    const today = new Date();

    const formatted = today.toISOString().split('T')[0];

    await this.dateTextbox.fill(formatted);

}

async selectDateAfter(days: number) {

    const date = new Date();

    date.setDate(date.getDate() + days);

    const formatted = date.toISOString().split('T')[0];

    await this.dateTextbox.fill(formatted);

}

}