import { Locator, Page, expect } from "@playwright/test";

export class MyTripsPage {

    constructor(readonly page: Page) { }

    private bookingStatus = (): Locator => this.page.locator('[data-id="state"]').first();
    private myTripsHeader = () : Locator => this.page.getByRole('heading',{name:'My Trips'});
    private cancelBookingButton = () : Locator => this.page.getByRole('button',{name:'Cancel'});

    async verifyMyTripsPageLoaded() {
        await expect(this.page.url()).toContain('/my-trips');
        await expect(this.myTripsHeader()).toBeVisible();
    }

    async verifyBookingStatus(status: string) {
        await expect(this.bookingStatus()).toHaveText(status);
    }

    async cancelBooking(){
        await this.cancelBookingButton().click();
    }

}