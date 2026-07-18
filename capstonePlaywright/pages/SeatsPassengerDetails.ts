import { Page, expect } from "@playwright/test";
import { Logger } from "../src/utils/Logger";

export class BookingPage {

    constructor(private page: Page) {}

    async selectBus() {

        Logger.info("Selecting A/C Sleeper filter");

        await this.page.getByRole('checkbox', { name: 'A/C Sleeper' }).check();

        Logger.info("Selecting KPN Travels");

        await this.page
            .getByLabel('KPN Travels')
            .getByRole('button', { name: 'Select Seats' })
            .click();

        Logger.info("Bus selected successfully");
    }

    async selectSeat() {

        Logger.info("Selecting boarding point");

        await this.page.locator('.swatch.ladies').click();

        await this.page.getByText('Chandigarh Highway Toll Plaza').click();

        Logger.info("Selecting available seat");

        const seatButton =this.page
        .getByRole("button", { name: /Seat .* available/ })
        .first();

        await expect(seatButton).toBeVisible();

        await seatButton.click();

        Logger.info("Seat selected successfully");

        await this.page
            .getByRole('button', {
                name: 'Continue to passenger details'
            })
            .click();

        Logger.info("Navigated to Passenger Details page");
    }

    async fillPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        email: string,
        phone: string
    ) {

        Logger.info("Entering passenger details");

        await this.page
            .getByRole('textbox', {
                name: /First name/
            })
            .fill(firstName);

        await this.page
            .getByRole('textbox', {
                name: /Last name/
            })
            .fill(lastName);

        await this.page
            .getByRole('spinbutton', {
                name: /Age/
            })
            .fill(age);

        await this.page
            .getByRole('textbox', {
                name: 'Email'
            })
            .fill(email);

        await this.page
            .getByRole('textbox', {
                name: 'Phone number'
            })
            .fill(phone);

        Logger.info("Passenger details entered");
    }

    async continueToPayment() {

        Logger.info("Proceeding to Payment page");

        await this.page
            .getByRole('button', {
                name: 'Continue to payment'
            })
            .click();

        Logger.info("Payment page opened");
    }

    async backToSeatSelection() {

        Logger.info("Navigating back to Seat Selection");

        await this.page
            .getByRole('link', {
                name: '← Back'
            })
            .click();

        Logger.info("Returned to Seat Selection page");
    }
}