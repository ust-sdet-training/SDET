import { Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../src/logger";

export class SeatSelectionPage extends BasePage {
  constructor(page: Page, log: AppLogger) {
    super(page, log);
  }

  readonly availableLabel = this.page.getByText("Available");

  async selectSeat(seatLabel: string) {
    this.log.info(`Selecting seat: ${seatLabel}`);
    await this.verifyVisible(this.availableLabel);
    await this.click(this.page.getByLabel(seatLabel));
  }
}