import { Page } from '@playwright/test';

export class SeatMapPage {

    constructor(
        private page: Page
    ) {}

    async selectSeat(
        preferredSeat: string = 'L3'
    ) {

        const preferredSeatLocator =
            this.page.getByRole(
                'button',
                {
                    name:
                        `Seat ${preferredSeat} available`
                }
            );

        if (
            await preferredSeatLocator.count() > 0
        ) {

            await preferredSeatLocator.click();

            console.log(
                `Selected preferred seat: ${preferredSeat}`
            );

            return;
        }

        const fallbackSeat =
            this.page.locator(
                'button[aria-label*="available"]'
            ).first();

        await fallbackSeat.waitFor({
            state: 'visible',
            timeout: 10000
        });

        await fallbackSeat.click();

        console.log(
            `Preferred seat ${preferredSeat} unavailable. Selected first available seat instead.`
        );
    }

    async selectBoardingPoint() {

        const boardingPoint =
            this.page
                .getByText(
                    'Bengaluru Railway Station'
                )
                .first();

        if (
            await boardingPoint.count() > 0
        ) {
            await boardingPoint.click();
        }
    }

    async continueBooking() {

        const continueButton =
            this.page.getByRole(
                'button',
                {
                    name:
                        /continue|proceed|book|checkout/i
                }
            );

        if (
            await continueButton.count() > 0
        ) {
            await continueButton.click();
        }
    }
}