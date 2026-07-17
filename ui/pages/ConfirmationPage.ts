import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class ConfirmationPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }
    // Locators
    private pnr = () => this.page.locator('[data-id="pnr"]');

    private bookingStatus = () =>
        this.page.locator('[data-id="state"]');

    async verifyBookingConfirmed(): Promise<string> {

        await expect(this.pnr()).toBeVisible();

        await expect(this.bookingStatus())
            .toHaveText(/Confirmed/i);

        const pnr =
            (await this.pnr().textContent())?.trim() ?? '';

        console.log(`PNR : ${pnr}`);

        const employeeId = process.env.EMPLOYEE_ID!;

        expect(pnr).toMatch(
            new RegExp(`^TS-${employeeId}-\\d{4}$`)
        );

        return pnr;

    }

}