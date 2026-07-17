import { Locator, Page } from "@playwright/test";

export class ConfirmationLocators {
  constructor(private page: Page) {}

  confirmationHeading(): Locator {
    return this.page.getByRole("heading").first();
  }

  confirmationText(): Locator {
    return this.page.getByText(/booking|ticket|confirmed|pnr/i).first();
  }
}
