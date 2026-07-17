import { expect, Locator, Page } from '@playwright/test';

export class SearchResultPage {

    readonly page: Page;

    readonly bookButton: Locator;
    readonly availableSeats: Locator;
    readonly continueButton: Locator;

    constructor(page: Page) {

        this.page = page;

        this.bookButton = page
            .getByRole('button', { name: 'Book' })
            .first();

        this.availableSeats = page.locator('div.seat.available');

        this.continueButton = page.getByRole('button', {
            name: 'Continue to passenger details'
        });

    }

    async verifySearchResultsLoaded() {

        await expect(this.bookButton).toBeVisible();

    }

    async clickBook() {

        await this.bookButton.click();

    }

    async selectSeat() {

    const seats = this.page.locator('div.seat.available');

    const count = await seats.count();

    for (let i = 0; i < count; i++) {

        const seat = seats.nth(i);

        const seatNo = await seat.getAttribute('data-seat');

        console.log(`Trying seat: ${seatNo}`);

        await seat.click();

        await this.page.waitForTimeout(500);

        // If the button became enabled, the seat was accepted
        if (await this.continueButton.isEnabled()) {
            console.log(`Selected seat: ${seatNo}`);
            return;
        }
    }

    throw new Error('No selectable seat found.');
}

    async continueToPassengerDetails() {

        await expect(this.continueButton).toBeVisible();

        await expect(this.continueButton).toBeEnabled({
            timeout: 30000
        });

        await this.continueButton.click();

    }

    

}