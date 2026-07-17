import { expect, type Page } from "@playwright/test";

export class PaymentPage {

    constructor(private page: Page) {}

    async payment() {

        await this.page.getByRole('checkbox', { name: 'A/C Sleeper' }).check();

        await this.page
            .getByLabel('KPN Travels')
            .getByRole('button', { name: 'Select Seats' })
            .click();

        await this.page.locator('.swatch.ladies').click();

        await this.page.getByText('Chandigarh Highway Toll Plaza').click();

        await this.page
            .getByRole('button', { name: 'Seat L3 available' })
            .click();

        await this.page
            .getByRole('button', { name: 'Continue to passenger details' })
            .click();

        await this.page
            .getByRole('textbox', { name: 'First name (seat L3)' })
            .click();

        await this.page
            .getByRole('textbox', { name: 'First name (seat L3)' })
            .fill('Peggy');

        await this.page
            .getByRole('textbox', { name: 'Last name (seat L3)' })
            .fill('P');

        await this.page
            .getByRole('spinbutton', { name: 'Age (seat L3)' })
            .fill('22');

        await this.page
            .getByRole('textbox', { name: 'Email' })
            .fill('peggy@tripstack.test');

        await this.page
            .getByRole('textbox', { name: 'Phone number' })
            .fill('9087789065');

        await this.page
            .getByRole('button', { name: 'Continue to payment' })
            .click();

        // If the application asks again after validation

        const lastName = this.page.getByRole('textbox', {
            name: 'Last name (seat L3)'
        });

        if (await lastName.isVisible().catch(() => false)) {

            await lastName.fill("P");

            await this.page
                .getByRole('button', { name: 'Continue to payment' })
                .click();
        }

        await expect(
            this.page.getByRole('textbox', { name: 'Name on card' })
        ).toBeVisible();

        await this.page
            .getByRole('textbox', { name: 'Name on card' })
            .fill('Peggy');

        await this.page
            .getByRole('textbox', { name: 'Card number' })
            .fill('123443215678');

        await this.page
            .getByRole('textbox', { name: 'Expiry' })
            .fill('12/28');

        await this.page
            .getByRole('textbox', { name: 'CVV' })
            .fill('1234');

        await this.page
            .getByRole('button', { name: /Pay ₹/ })
            .click();

    }

}