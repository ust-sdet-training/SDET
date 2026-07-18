import { expect, Page } from "@playwright/test";

import { BasePage } from "../base/BasePage";
import { MyTripsLocators } from "../locators/MyTripsLocators";

export class MyTripsPage extends BasePage {

    readonly locator: MyTripsLocators;

    constructor(page: Page) {

        super(page);

        this.locator =
            new MyTripsLocators(page);

    }

    async verifyPageLoaded() {

        await expect(
            this.locator.pageTitle
        ).toBeVisible();

        await expect(
            this.locator.tripsList
        ).toBeVisible();

    }

    async getBookingCount(): Promise<number> {

        return await this.locator.tripCards.count();

    }

    async verifyMinimumBookings(count: number) {

        expect(
            await this.getBookingCount()
        ).toBeGreaterThanOrEqual(count);

    }

    async verifyBookingExists(pnr: string) {

        await expect(
            this.locator.pnrList.filter({
                hasText: pnr
            })
        ).toBeVisible();

    }

    async verifyBookingStatus(
        pnr: string,
        expectedStatus: string
    ) {

        const total =
            await this.locator.tripCards.count();

        for (let i = 0; i < total; i++) {

            const card =
                this.locator.tripCards.nth(i);

            const bookingPNR =
                await card.locator("[data-id='pnr']").textContent();

            if (bookingPNR?.trim() === pnr) {

                await expect(
                    card.locator("[data-id='state']")
                ).toHaveText(expectedStatus);

                return;

            }

        }

        throw new Error(
            `Booking ${pnr} not found`
        );

    }

    async printAllBookings() {

        const total =
            await this.locator.tripCards.count();

        console.log("Total Bookings :", total);

        for (let i = 0; i < total; i++) {

            const card =
                this.locator.tripCards.nth(i);

            const pnr =
                await card.locator("[data-id='pnr']").textContent();

            const status =
                await card.locator("[data-id='state']").textContent();

            console.log(
                `${pnr}  -->  ${status}`
            );

        }

    }

}