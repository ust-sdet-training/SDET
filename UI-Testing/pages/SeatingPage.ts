import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class SeatingPage extends BasePage {
    readonly seatingText: Locator
    readonly continueBtn: Locator
    readonly availableSeats:Locator
    readonly selectedSeat: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log)

        this.seatingText = page.getByText("Choose your seats")
        this.continueBtn = page.getByRole("button", {name: "Continue to passenger details"})
        this.availableSeats = page.locator(".seat.available");
        this.selectedSeat = page.locator(".seat.selected");
    }

    async verifySeatingPage(): Promise<void> {
        await expect(this.isVisible(this.seatingText))
    }

    // async selectAvailableSeat(): Promise<string> {
    //     this.log.info("Selecting first available seat");
    //     const seat = this.availableSeats.first();
    //     const seatNumber = await seat.getAttribute("data-seat");
    //     await this.click(seat);
    //     this.log.info(`Selected seat: ${seatNumber}`);
    //     return seatNumber!;
    // }

    async selectAvailableSeat(): Promise<string> {
        const seats = this.availableSeats;
        const count = await seats.count();
        for (let i = 0; i < count; i++) {
            const seat = seats.nth(i);
            const seatNumber = await seat.getAttribute("data-seat");
            try {
                await seat.click();
                await expect(seat).toHaveClass(/selected/);
                this.log.info(`Selected seat: ${seatNumber}`);
                return seatNumber!;

            } catch {
                this.log.warn(`Seat ${seatNumber} could not be selected. Trying next seat...`);
            }
        }
        throw new Error("No selectable seats found.");
    }

    async verifySeatSelected(seatNumber: string): Promise<void> {
        await expect(this.page.locator(`[data-seat="${seatNumber}"]`)).toHaveClass(/selected/);
    }

    async clickContinue(): Promise<void> {
        await this.click(this.continueBtn)
    }
}