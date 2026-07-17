import { expect, Page } from '@playwright/test';
import { DateUtil } from '../utils/DateUtil';
import { BasePage } from './BasePage';

export class HomePage extends BasePage {

    constructor(page: Page) {
        super(page);
    }
    // Locators
    private from = () => this.page.locator('#home-from');
    private to = () => this.page.locator('#home-to');
    private suggestion = () => this.page.getByRole('option').first();
    private travelDate = () => this.page.locator('#home-date');
    private searchButton = () => this.page.locator('button[type="submit"]');

    // Search Flight
    async searchFlight(from: string, to: string, daysFromToday: number) {

        // From
        await this.from().fill(from);
        await this.suggestion().click();

        // To
        await this.to().fill(to);
        await this.suggestion().click();

        // Travel Date
        const date = DateUtil.getTravelDate(daysFromToday);

        await this.travelDate().fill(date);

        // Search
        await this.searchButton().click();

        // Verify Search Results Page
        await expect(this.page).toHaveURL(/flights\/results/);

    }

}