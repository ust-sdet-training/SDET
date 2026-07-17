import { Page } from "@playwright/test";
import { SeatLocators } from "../locators/SeatLocators";
import { logger } from "../logger/Logger";

export class SeatPage {
  private locators: SeatLocators;

  constructor(private page: Page) {
    this.locators = new SeatLocators(page);
  }

  async selectFirstAvailableSeat() {
    const availableSeat = this.page.locator("div.seat.available").first();
    await availableSeat.waitFor({ state: "visible" });
    const seatText = (await availableSeat.textContent())?.trim() || "";
    logger.info(`Selecting first available seat ${seatText}`);
    await availableSeat.click();
    return seatText;
  }

  async continueToPassenger() {
    const continueButton = this.page
      .locator("button#continue-btn:not([disabled])")
      .first();
    await continueButton.waitFor({ state: "visible" });
    await continueButton.click();
  }

  async selectSeatByNumber(seatNo: string) {
    const seatLocator = this.locators.seatByNumber(seatNo);
    await seatLocator.waitFor({ state: "visible" });
    logger.info(`Selecting seat by number ${seatNo}`);
    await seatLocator.click();
  }
}
