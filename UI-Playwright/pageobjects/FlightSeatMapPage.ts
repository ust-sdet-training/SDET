import { Locator, Page, expect } from "@playwright/test";

export class FlightSeatMapPage {

    constructor(readonly page: Page) { }

    private selectSeat = (seat: string): Locator => this.page.locator(`[data-seat='${seat}']`);
    private continueButton = (): Locator => this.page.getByRole('button', { name: 'Continue to passenger details' });
    private seatsHeader = (): Locator => this.page.getByRole('heading', { name: 'Choose your seats' });

    async verifyFlightSeatMapPageLoaded() {
        await expect(this.page.url()).toContain('/seatmap');
        await expect(this.seatsHeader()).toBeVisible();
    }

    async bookSeat(seat: string) {
        await this.selectSeat(seat).click();
        await expect(this.selectSeat(seat)).toHaveAttribute('aria-pressed','true');
        await expect(this.continueButton()).toBeEnabled();
        await this.continueButton().click();
    }

}