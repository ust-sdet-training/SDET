import { Locator, Page } from "@playwright/test";

export class ResultsLocators {
  constructor(private page: Page) {}

  heading(): Locator {
    return this.page.getByRole("heading", { name: /flights:/i }).first();
  }

  firstBookButton(): Locator {
    return this.page.getByRole("button", { name: /^book$/i }).first();
  }
}
