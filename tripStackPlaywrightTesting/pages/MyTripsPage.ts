import { expect, Locator, Page } from '@playwright/test';

export class MyTripsPage {

    readonly page: Page;

    readonly myTripsHeading: Locator;
    readonly confirmedBookings: Locator;
    readonly cancelButtons: Locator;

    constructor(page: Page) {

        this.page = page;

        this.myTripsHeading = page.getByRole('heading', {
            name: 'My Trips'
        });

        this.confirmedBookings = page.locator(
            'span[data-id="state"]'
        );

        this.cancelButtons = page.getByRole('button', {
            name: 'Cancel'
        });

    }

    async verifyMyTripsPageLoaded() {

        await expect(this.myTripsHeading).toBeVisible();

    }

    async verifyBookingExists() {

        await expect(this.confirmedBookings.first()).toBeVisible();

    }

    async verifyRoundTripBookings() {

        await expect(this.confirmedBookings.first()).toBeVisible();

        const totalBookings =
            await this.confirmedBookings.count();

        expect(totalBookings).toBeGreaterThanOrEqual(2);

    }

    async getTotalBookings(): Promise<number> {

        return await this.confirmedBookings.count();

    }

    async clickFirstCancel() {

        await expect(
            this.cancelButtons.first()
        ).toBeVisible();

        await this.cancelButtons.first().click();

    }

}