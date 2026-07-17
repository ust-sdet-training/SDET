import { expect, Locator, Page } from '@playwright/test';

export class BookingConfirmationPage {

    readonly page: Page;

    readonly successHeading: Locator;
    readonly confirmationMessage: Locator;
    readonly bookingReference: Locator;
    readonly journeyLabel: Locator;
    readonly flightLabel: Locator;
    readonly seatsLabel: Locator;
    readonly amountPaidLabel: Locator;
    readonly amountPaid: Locator;
    readonly viewMyTripsButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.successHeading = page.getByRole('heading', {
            name: "You're all set! 🎉"
        });

        this.confirmationMessage = page.getByText(
            'Your booking is confirmed. A'
        );

        this.bookingReference = page.getByText('Booking reference (PNR)');

        this.journeyLabel = page.getByText('Journey');

        this.flightLabel = page.getByText('Flight', {
            exact: true
        });

        this.seatsLabel = page.getByText('Seats', {
            exact: true
        });

        this.amountPaidLabel = page.getByText('Amount paid');

        this.amountPaid = page.getByText('₹');

        this.viewMyTripsButton = page.getByRole('button', {
            name: 'View my trips'
        });

    }

    async verifyBookingSuccess() {

        await expect(this.successHeading).toBeVisible();

        await expect(this.confirmationMessage).toBeVisible();

        await expect(this.bookingReference).toBeVisible();

    }

    async verifyJourneyDetails() {

        await expect(this.journeyLabel).toBeVisible();

        await expect(this.flightLabel).toBeVisible();

        await expect(this.seatsLabel).toBeVisible();

        await expect(this.amountPaidLabel).toBeVisible();

        await expect(this.amountPaid).toBeVisible();

    }

    async getBookingReference(): Promise<string> {

        const bookingText =
            await this.bookingReference.textContent();

        return bookingText ?? '';

    }

    async clickViewMyTrips() {

        await expect(this.viewMyTripsButton).toBeEnabled();

        await this.viewMyTripsButton.click();

    }

}