import { Page, expect } from '@playwright/test';
import { Logger } from '../src/logger/logger';

export class ConfirmationPage {

    constructor(private page: Page) {}

    private bookingReference = () =>
        this.page.locator('text=/TS-\\d{4}-\\d{4}/');

    private amountPaid = () =>
        this.page.locator('text=Amount paid');

    private myTripsButton = () =>
        this.page.getByRole('button', {
            name: /View my trips/i
        });

    async verifyBookingSuccessful() {

        await expect(
            this.page.getByText("You're all set!")
        ).toBeVisible({
            timeout: 30000
        });

        Logger.success("Booking Confirmed");

    }

    async verifyPNR() {

        await expect(
            this.bookingReference()
        ).toBeVisible();

        Logger.success("PNR Generated");

    }

    async verifyAmountPaid() {

        await expect(
            this.amountPaid()
        ).toBeVisible();

    }

    async openMyTrips() {

        await this.myTripsButton().click();

    }

}