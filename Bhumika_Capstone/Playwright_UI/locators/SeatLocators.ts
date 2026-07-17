import { Locator, Page } from "@playwright/test";

export class SeatLocators {
  constructor(private page: Page) {}

  seatButtons(): Locator {
    return this.page.locator("div.seat.available");
  }

  continueButton(): Locator {
    return this.page.locator("button#continue-btn");
  }

  fallbackButton(): Locator {
    return this.page.locator("button#book-button");
  }

  seatByNumber(seatNo: string): Locator {
    // matches visible seat labels like "1A" or ARIA labelled buttons
    return this.page.locator(`[data-seat="${seatNo}"]`);
  }
}
