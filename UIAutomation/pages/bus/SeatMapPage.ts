import { Page } from '@playwright/test';
import { BOARDING_POINT_TEXT, DROPPING_POINT_TEXT } from '../../utils/constants';

export class SeatMapPage {

    constructor(private page: Page) {}

    async selectSeat(preferredSeat?: string): Promise<string> {

        if (preferredSeat) {
            const preferredSeatLocator = this.page.getByRole(
                'button',
                { name: `Seat ${preferredSeat} available` }
            );

            if (await preferredSeatLocator.count() > 0) {
                await preferredSeatLocator.click();
                console.log(`Selected preferred seat: ${preferredSeat}`);
                return preferredSeat;
            }
        }

        const fallbackSeat = this.page
            .getByRole('button', { name: /^Seat .+ available$/ })
            .first();

        await fallbackSeat.waitFor({ state: 'visible', timeout: 10000 });

        const seatId = await fallbackSeat.getAttribute('data-seat');
        if (!seatId) {
            throw new Error('Could not determine seat id from data-seat attribute.');
        }

        await fallbackSeat.click();
        console.log(`Selected first available seat: ${seatId}`);
        return seatId;
    }

    async selectBoardingPoint() {
        const boardingPoint = this.page
            .getByText(new RegExp(`^${BOARDING_POINT_TEXT}`))
            .first();

        await boardingPoint.waitFor({ state: 'visible', timeout: 10000 });
        await boardingPoint.click();
    }

    async selectDroppingPoint() {
        const droppingPoint = this.page
            .getByText(new RegExp(`^${DROPPING_POINT_TEXT}`))
            .first();

        await droppingPoint.waitFor({ state: 'visible', timeout: 10000 });
        await droppingPoint.click();
    }

    async continueBooking() {
        const continueButton = this.page.locator('#continue-btn');
        await continueButton.waitFor({ state: 'visible', timeout: 10000 });
        await continueButton.click();
    }

    async getAvailableSeatCount(): Promise<number> {
    // Counts by accessible name pattern, not by CSS class or tag —
    // resilient to visual/structural changes to the seat map.
    return await this.page
        .getByRole('button', { name: /^Seat .+ available$/ })
        .count();
    }
}