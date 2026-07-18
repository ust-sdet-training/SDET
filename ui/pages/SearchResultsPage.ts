import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class SearchResultsPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    // Locators
    private firstBookButton = () =>
        this.page
            .getByRole('region', { name: 'Flight results' })
            .getByRole('button', { name: 'Book' })
            .first();

    // Verify Results
    async verifyResults() {

        await expect(this.firstBookButton()).toBeVisible();

    }

    // Select First Flight
    async selectFirstFlight() {

        await this.verifyResults();

        await this.firstBookButton().click();

    }

}
