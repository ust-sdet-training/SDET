import { expect, Page } from '@playwright/test';
import { RandomUtil } from '../utils/RandomUtil';
import { BasePage } from './BasePage';

export class SeatSelectionPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    // Locators

    private cabin = () =>
        this.page.locator('[data-layout="cabin"]');

    private availableSeats = () =>
        this.page.locator('.seat.available');

    private continueButton = () =>
        this.page.locator('#continue-btn');

    // Verify Seat Map

    async verifySeatMap() {

        await expect(this.cabin()).toBeVisible();

    }

    // Select Random Seat

    async selectRandomSeat() {

        await this.verifySeatMap();

        const seatCount = await this.availableSeats().count();

        expect(seatCount).toBeGreaterThan(0);

        await RandomUtil.clickRandom(this.availableSeats());

    }

    // Continue

    async continueBooking() {

        await this.continueButton().click();

    }

    // Complete Seat Selection

    async selectSeatAndContinue() {

        await this.selectRandomSeat();

        await this.continueBooking();

        await expect(this.page).toHaveURL(/book\/passenger/);

    }

}