import { expect, Page } from "@playwright/test";

export class MyTripsPage {

    constructor(private page: Page) {}

    async verifyBooking() {

        await expect(this.page.getByText("Your booking is confirmed")).toBeVisible();

        await this.page.getByRole("button", {name: "View my trips" }).click();

        await expect(this.page.locator("[data-id='pnr']").first()).toContainText("TS-");

        await expect(this.page.locator("[data-id='state']").first()).toHaveText("CONFIRMED");
    }

    async getPNR() {

        return (await this.page.locator("[data-id='pnr']").first().textContent())!.trim();
    }

}