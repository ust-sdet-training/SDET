import { expect, Locator, Page } from '@playwright/test';

export class MyTripsPage {

    readonly page: Page;

    readonly myTripsHeading: Locator;
    readonly bookingCard: Locator;
    readonly cancelButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.myTripsHeading = page.getByRole('heading', {
            name: 'My Trips'
        });

        this.bookingCard = page.getByText('CONFIRMED');

        this.cancelButton = page.getByRole('button', {
            name: 'Cancel'
        });

    }

    async verifyMyTripsPageLoaded() {

        await expect(this.myTripsHeading).toBeVisible();

    }

    async verifyBookingExists() {

        await expect(this.bookingCard).toBeVisible();

    }

    async getBookingDetails(): Promise<string> {

        const booking = await this.bookingCard.textContent();

        return booking ?? '';

    }

    async clickCancel() {

        await expect(this.cancelButton).toBeVisible();

        await this.cancelButton.click();

    }

}