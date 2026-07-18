import { expect, Page } from '@playwright/test';
import { Logger } from '../src/logger/logger';

export class MyTripsPage {

    constructor(private page: Page) {}

    async verifyTripExists() {

        await expect(
            this.page.getByRole('heading', {
                name: /My Trips/i
            })
        ).toBeVisible();

        Logger.success("Trip found in My Trips");

    }

}