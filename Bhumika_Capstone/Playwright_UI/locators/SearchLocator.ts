import { Locator, Page } from "@playwright/test";

export class SearchLocators {
  constructor(private page: Page) {}

  fromInput(): Locator {
    return this.page.getByRole("combobox", { name: /from/i }).first();
  }

  toInput(): Locator {
    return this.page.getByRole("combobox", { name: /to/i }).first();
  }

  departureDateGrid(): Locator {
    return this.page.getByRole("textbox", { name: /departure date/i }).first();
  }

  searchButton(): Locator {
    return this.page
      .getByRole("button", { name: /search flights/i })
      .first();
  }
}
