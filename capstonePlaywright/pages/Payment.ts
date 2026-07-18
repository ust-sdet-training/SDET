import { expect, type Page } from "@playwright/test";
import { Logger } from "../src/utils/Logger";

export class PaymentPage {

    constructor(private page: Page) {}

    async payment() {

        Logger.info("Selecting A/C Sleeper filter");

        await this.page.getByRole('checkbox', {
            name: 'A/C Sleeper'
        }).check();

        Logger.info("Opening seat selection");

        await this.page
            .getByLabel('KPN Travels')
            .getByRole('button', { name: 'Select Seats' })
            .click();

        Logger.info("Selecting ladies seat filter");

        await this.page.locator('.swatch.ladies').click();

        Logger.info("Selecting boarding point");

        await this.page.getByText("Chandigarh Highway Toll Plaza")
        .first()
        .click();

        Logger.info("Selecting available seat");

        const availableSeat =this.page
        .locator("[data-state='available'][role='button']")
        .first();

        await expect(availableSeat).toBeVisible();

        await availableSeat.click();

        Logger.success("Seat selected successfully");

        Logger.info("Continuing to passenger details");

        await expect(this.page.getByRole("button", {
        name: "Continue to passenger details"
        })).toBeVisible();

        await this.page.getByRole("button", {
        name: "Continue to passenger details"})
        .click();

        Logger.info("Entering passenger details");

        await this.page
            .getByRole('textbox', {
                name: /First name/
            })
            .fill('Peggy');

        await this.page
            .getByRole('textbox', {
                name: /Last name/
            })
            .fill('P');

        await this.page
            .getByRole('spinbutton', {
                name: /Age/
            })
            .fill('22');

        await this.page
            .getByRole('textbox', {
                name: 'Email'
            })
            .fill('peggy@tripstack.test');

        await this.page
            .getByRole('textbox', {
                name: 'Phone number'
            })
            .fill('9087789065');

        Logger.success("Passenger details entered");

        Logger.info("Proceeding to payment");

        await this.page
            .getByRole('button', {
                name: 'Continue to payment'
            })
            .click();

        // Validation page appears again sometimes

        const lastName = this.page.getByRole('textbox', {
            name: 'Last name (seat L3)'
        });

        if (await lastName.isVisible().catch(() => false)) {

            Logger.info("Validation page detected. Re-entering last name.");

            await lastName.fill("P");

            await this.page
                .getByRole('button', {
                    name: 'Continue to payment'
                })
                .click();
        }

        Logger.info("Payment page displayed");

        await expect(
            this.page.getByRole('textbox', {
                name: 'Name on card'
            })
        ).toBeVisible();

        Logger.info("Entering card details");

        await this.page
            .getByRole('textbox', {
                name: 'Name on card'
            })
            .fill('Peggy');

        await this.page
            .getByRole('textbox', {
                name: 'Card number'
            })
            .fill('123443215678');

        await this.page
            .getByRole('textbox', {
                name: 'Expiry'
            })
            .fill('12/28');

        await this.page
            .getByRole('textbox', {
                name: 'CVV'
            })
            .fill('1234');

        Logger.success("Card details entered");

        Logger.info("Submitting payment");

        await this.page
            .getByRole('button', {
                name: /Pay ₹/
            })
            .click();

        Logger.success("Payment submitted successfully");
    }
}