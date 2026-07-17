import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class SearchResultsPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    // Locators
    private flights = () => this.page.locator('#results article');

    private firstBookButton = () =>
        this.flights()
            .first()
            .locator('a[role="button"]');

    // Verify Results
    async verifyResults() {

        await expect(this.flights().first()).toBeVisible();

    }

    // Select First Flight
    async selectFirstFlight() {

        await this.verifyResults();

        await this.firstBookButton().click();

    }

}