import { Page } from "@playwright/test";

export class BusDetailPage {
  constructor(private readonly page: Page) {}

  async selectSeat(): Promise<string> {
    const seatNumber = "L3";

    await this.page
      .getByRole("button", { name: `Seat ${seatNumber} available` })
      .click();

    await this.page
      .getByRole("button", { name: "Continue to passenger details" })
      .click();

    return seatNumber;
  }
}