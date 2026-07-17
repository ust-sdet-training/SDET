import { expect, Locator, Page } from '@playwright/test';

export class MyTripsPage {

    readonly page: Page;

    readonly myTripsHeading: Locator;
    readonly bookingCards: Locator;
    readonly latestBooking: Locator;
    readonly cancelButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.myTripsHeading = page.getByRole('heading', {
            name: 'My Trips'
        });

        // Your latest booking is displayed first
        this.bookingCards = page.locator('[data-id="booking-card"]');

        // Fallback if booking cards don't have a data-id
        this.latestBooking = page
            .getByText(new RegExp(`TS-${process.env.EMPLOYEE_ID}-`))
            .first();

        this.cancelButton = page
            .getByRole('button', {
                name: 'Cancel'
            })
            .first();

    }

    async verifyMyTripsPageLoaded() {

        await expect(this.myTripsHeading).toBeVisible();

    }

    async verifyBookingExists() {

        await expect(this.latestBooking).toBeVisible();

    }

    async verifyBookingConfirmed() {

        await expect(
            this.page.getByText('CONFIRMED').first()
        ).toBeVisible();

    }

    async getBookingPNR(): Promise<string> {

        const text = await this.latestBooking.textContent();

        return text ?? '';

    }

    async clickCancel() {

        await expect(this.cancelButton).toBeVisible();

        await this.cancelButton.click();

    }

}