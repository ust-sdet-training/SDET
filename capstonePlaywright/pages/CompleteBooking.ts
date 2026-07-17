import { expect, Page } from "@playwright/test";

export class TrackTicketPage {

    constructor(private page: Page) {}

    nameOnCardTextbox() {
        return this.page.getByRole("textbox", {
            name: "Name on card"
        });
    }

    cardNumberTextbox() {
        return this.page.getByRole("textbox", {
            name: "Card number"
        });
    }

    expiryTextbox() {
        return this.page.getByRole("textbox", {
            name: "Expiry"
        });
    }

    cvvTextbox() {
        return this.page.getByRole("textbox", {
            name: "CVV"
        });
    }

    payButton() {
        return this.page.getByRole("button", {
            name: /Pay ₹/
        });
    }

    viewTicketButton() {
        return this.page.getByRole("button", {
            name: /View Ticket|View Track|Track Ticket/i
        });
    }

    async pay() {

        await expect(this.nameOnCardTextbox()).toBeVisible();

        await this.nameOnCardTextbox().fill("Peggy");

        await this.cardNumberTextbox().fill("123443215678");

        await this.expiryTextbox().fill("12/28");

        await this.cvvTextbox().fill("1234");

        await this.payButton().click();

    }

    async clickViewTrackButton() {

        await expect(this.viewTicketButton()).toBeVisible();

        await this.viewTicketButton().click();

    }

}