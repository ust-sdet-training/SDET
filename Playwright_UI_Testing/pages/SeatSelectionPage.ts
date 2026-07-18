import { expect, Page } from "@playwright/test";
import { SeatSelectionLocators } from "../locators/SeatSelectionLocators";

export class SeatSelectionPage {

    private locators: SeatSelectionLocators;

    constructor(private page: Page) {
        this.locators = new SeatSelectionLocators(page);
    }

    async verifySeatMapLoaded() {
        await expect(this.locators.availableSeats().first()).toBeVisible();
    }

    async selectLowerDeck() {
        await this.locators.lowerDeckButton().click();
    }

    async selectUpperDeck() {
        await this.locators.upperDeckButton().click();
    }

    async selectSeat(seatNo: string) {
        await this.locators.seatByNumber(seatNo).click();
    }

    async selectFirstAvailableSeat() {
        await this.locators.availableSeats().first().click();
    }

    async clickContinue() {
        await this.locators.continueButton().click();
    }

    async selectSeatAndContinue(
        seatNo: string,
        deck: "lower" | "upper" = "lower"
    ) {

        if (deck === "upper") {
            await this.selectUpperDeck();
        } else {
            await this.selectLowerDeck();
        }

        await this.selectSeat(seatNo);
        await this.clickContinue();
    }
}