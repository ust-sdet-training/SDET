import { expect, Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { SeatLocators } from "../locators/SeatLocators";

export class SeatPage extends BasePage {

    readonly locator: SeatLocators;

    constructor(page: Page) {
        super(page);
        this.locator = new SeatLocators(page);
    }

    async verifySeatPageLoaded() {

        await expect(this.locator.seatMap)
            .toBeVisible();

    }


    async selectFirstAvailableSeat(): Promise<string> {

    const seat = this.locator.availableSeats.first();

    const seatNumber =
        await seat.getAttribute("data-seat") ?? "";

    await seat.click();

    await expect(this.locator.selectedSeatLabel)
        .not.toHaveText("none");

    await expect(this.locator.selectedSeatLabel)
        .toContainText(seatNumber);

    return seatNumber;

}

async chooseSeatAndContinue(): Promise<string> {

    const seat =
        await this.selectFirstAvailableSeat();

    await this.continueToPassenger();

    return seat;

}


    async continueToPassenger() {

        await expect(this.locator.continueButton)
            .toBeEnabled();

        await this.locator.continueButton.click();

        await this.page.waitForLoadState("networkidle");

    }

    
    async getSelectedSeat(): Promise<string> {

        return await this.locator.selectedSeatLabel
            .textContent() ?? "";

    }

    async getSeatCount(): Promise<number> {

        return await this.locator.availableSeats.count();

    }

}