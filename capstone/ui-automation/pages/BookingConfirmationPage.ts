import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";


export class BookingConfirmationPage extends BasePage {

    readonly pnrNumber: Locator;
    readonly viewMyTripButton: Locator;


    constructor(page: Page) {
        super(page);

        this.pnrNumber = page.locator(".pnr");
        this.viewMyTripButton = page.getByRole("button", {
            name: "View My Trip"
        });
    }

    async getPNRNumber() {
        return await this.pnrNumber.textContent();
    }

    async clickViewMyTrip() {
        await this.viewMyTripButton.click();
        await expect(this.page).toHaveURL(/\/my-trips/);

    }

}