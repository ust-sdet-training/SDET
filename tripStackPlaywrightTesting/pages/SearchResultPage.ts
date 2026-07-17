import { expect, Locator, Page } from '@playwright/test';

export class SearchResultPage {

    readonly page: Page;

    readonly bookButton: Locator;
    readonly availableSeats: Locator;
    readonly continueButton: Locator;
    readonly seatUnavailableMessage: Locator;
    readonly backButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.bookButton = page
            .getByRole('button', { name: 'Book' })
            .first();

        // All currently available seats
        this.availableSeats = page.locator('div.seat.available');

        this.continueButton = page.getByRole('button', {
            name: 'Continue to passenger details'
        });

        this.seatUnavailableMessage = page.getByText(
            'Seat no longer available'
        );

        this.backButton = page.getByRole('button', {
            name: 'Back'
        });

    }

    async verifySearchResultsLoaded() {

        await expect(this.bookButton).toBeVisible();

    }

    async clickBook() {

        await this.bookButton.click();

        await expect(this.availableSeats.first()).toBeVisible();

    }

    /**
     * Returns the seat number that was clicked.
     * Skips seats that have already failed.
     */
    async selectSeat(
        triedSeats: Set<string>
    ): Promise<string> {

        const totalSeats = await this.availableSeats.count();

        if (totalSeats === 0) {
            throw new Error('No available seats found.');
        }

        for (let i = 0; i < totalSeats; i++) {

            const seat = this.availableSeats.nth(i);

            const seatNumber =
                await seat.getAttribute('data-seat');

            if (!seatNumber) {
                continue;
            }

            if (triedSeats.has(seatNumber)) {
                continue;
            }

            console.log(`Trying Seat : ${seatNumber}`);

            await seat.click();

            return seatNumber;

        }

        throw new Error(
            'No more seats available to try.'
        );

    }

    async isContinueButtonEnabled(): Promise<boolean> {

    return await this.continueButton.isEnabled();

}

async continueToPassengerDetails() {

    await this.continueButton.click();

}

    async isSeatUnavailable(): Promise<boolean> {

        return await this.seatUnavailableMessage
            .isVisible()
            .catch(() => false);

    }

    async goBackToSeatSelection() {

        await this.backButton.click();

        await expect(this.availableSeats.first())
            .toBeVisible();

    }

}