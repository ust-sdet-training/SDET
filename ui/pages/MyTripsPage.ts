import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class MyTripsPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }
    myTripsLink = () =>
        this.page.getByRole('link', {
            name: 'My Trips',
            exact: true
        });

    booking = (pnr: string) =>
        this.page.locator(`[data-id="trip-${pnr}"]`);

    cancelButton = (pnr: string) =>
        this.booking(pnr)
            .locator('button[type="submit"]');

    bookingStatus = (pnr: string) =>
        this.booking(pnr)
            .locator('[data-id="state"]');

    async openMyTrips() {

        await this.myTripsLink().click();

        await expect(this.page)
            .toHaveURL(/my-trips/);

    }

    async verifyBooking(pnr: string) {

        await expect(
            this.booking(pnr)
        ).toBeVisible();

    }

    async cancelBooking(pnr: string) {

        await this.cancelButton(pnr).click();

        await expect(
            this.bookingStatus(pnr)
        ).toHaveText(/Refunded/i);

    }

}