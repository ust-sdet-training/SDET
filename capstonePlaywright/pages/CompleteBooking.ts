import { expect, Page } from "@playwright/test";
import { Logger } from "../src/utils/Logger";

export class TrackTicketPage {

    constructor(private page: Page) {}

    private nameOnCardTextbox() {
        return this.page.getByRole("textbox", {
            name: "Name on card"
        });
    }

    private cardNumberTextbox() {
        return this.page.getByRole("textbox", {
            name: "Card number"
        });
    }

    private expiryTextbox() {
        return this.page.getByRole("textbox", {
            name: "Expiry"
        });
    }

    private cvvTextbox() {
        return this.page.getByRole("textbox", {
            name: "CVV"
        });
    }

    private payButton() {
        return this.page.getByRole("button", {
            name: /Pay ₹/
        });
    }

    private bookingHeading() {
        return this.page.getByRole("heading", {
            name: /You're all set!/i
        });
    }

    private viewMyTripsButton() {
        return this.page.getByRole("button", {
            name: "View my trips"
        });
    }

    async pay() {

        Logger.info("Waiting for Payment Page");

        await expect(this.nameOnCardTextbox()).toBeVisible();

        Logger.info("Entering Card Holder Name");
        await this.nameOnCardTextbox().fill("Peggy");

        Logger.info("Entering Card Number");
        await this.cardNumberTextbox().fill("123443215678");

        Logger.info("Entering Expiry");
        await this.expiryTextbox().fill("12/28");

        Logger.info("Entering CVV");
        await this.cvvTextbox().fill("1234");

        Logger.info("Clicking Pay");

        await this.payButton().click();

        Logger.success("Payment Submitted");
    }

    async openMyTrips() {

        Logger.info("Waiting for Booking Confirmation");

        await expect(this.bookingHeading()).toBeVisible({
            timeout: 15000
        });

        Logger.success("Booking Confirmed");

        Logger.info("Waiting for View My Trips button");

        await expect(this.viewMyTripsButton()).toBeVisible();

        Logger.info("Clicking View My Trips");

        await this.viewMyTripsButton().click();

        await expect(this.page).toHaveURL(/my-trips/);

        Logger.success("My Trips page opened successfully");
    }

}