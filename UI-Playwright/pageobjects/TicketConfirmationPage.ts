import { Locator, Page, expect } from "@playwright/test";

export class TicketConfirmationPage {

    constructor(readonly page: Page) { }

    private confirmedText = () : Locator => this.page.getByText('CONFIRMED', { exact: true });
    private viewMyTripsButton = () : Locator => this.page.getByRole('button', { name: 'View my trips' });

    async verifyTicketConfirmationPageLoaded() {
        await expect(this.page.url()).toContain('/book/confirmation');
        await expect(this.viewMyTripsButton()).toBeVisible();
    }

    async verifyConfirmedStatus() {
       await expect(this.confirmedText()).toBeVisible();
    }

    async clickViewMyTrips(){
        await this.viewMyTripsButton().click();
    }


}