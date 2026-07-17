import { Page } from "@playwright/test";

export class BookingPage {

    constructor(private page: Page) {}

    async selectBus() {

        await this.page.getByRole('checkbox', { name: 'A/C Sleeper' }).check();

        await this.page
            .getByLabel('KPN Travels')
            .getByRole('button', { name: 'Select Seats' })
            .click();

    }

    async selectSeat() {

        await this.page.locator('.swatch.ladies').click();

        await this.page.getByText('Chandigarh Highway Toll Plaza').click();

        await this.page
            .getByRole('button', { name: 'Seat L3 available' })
            .click();

        await this.page
            .getByRole('button', { name: 'Continue to passenger details' })
            .click();

    }

    async fillPassengerDetails(
        firstName: string,
        lastName: string,
        age: string,
        email: string,
        phone: string
    ) {

        await this.page
            .getByRole('textbox', { name: 'First name (seat L3)' })
            .fill(firstName);

        await this.page
            .getByRole('textbox', { name: 'Last name (seat L3)' })
            .fill(lastName);

        await this.page
            .getByRole('spinbutton', { name: 'Age (seat L3)' })
            .fill(age);

        await this.page
            .getByRole('textbox', { name: 'Email' })
            .fill(email);

        await this.page
            .getByRole('textbox', { name: 'Phone number' })
            .fill(phone);

    }

    async continueToPayment() {

        await this.page
            .getByRole('button', { name: 'Continue to payment' })
            .click();

    }

    async backToSeatSelection() {

        await this.page
            .getByRole('link', { name: '← Back' })
            .click();

    }

}