import { expect, Locator, Page } from "@playwright/test";

export class BookingPage {
  readonly page: Page;
  readonly busTab: Locator;
  readonly fromInput: Locator;
  readonly toInput: Locator;
  readonly dateInput: Locator;
  readonly searchButton: Locator;
  readonly continueButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.busTab = page.getByRole("tab", { name: "Buses" });
    this.fromInput = page.getByRole("combobox", { name: "From" });
    this.toInput = page.getByRole("combobox", { name: "To" });
    this.dateInput = page.getByRole("textbox", { name: "Date" });
    this.searchButton = page.getByRole("button", { name: "Search" });
    this.continueButton = page.locator("#continue-btn");
  }

  async chooseBusRoute(from: string, to: string, date: string) {
    await this.busTab.click();
    await this.fromInput.click();
    await this.fromInput.fill(from);
    await this.page.getByRole("option", { name: `Goa GOI` }).click();
    await this.toInput.click();
    await this.page.getByRole("option", { name: `Bengaluru BLR` }).click();
    await this.dateInput.fill(date);
    await this.searchButton.click();
  }

  // Use the visible datepicker to select a date (simulates real user interaction)
  async chooseBusRouteWithDatePicker(from: string, to: string, date: string) {
    await this.busTab.click();
    await this.fromInput.click();
    await this.fromInput.fill(from);
    await this.page.getByRole("option", { name: `Goa GOI` }).click();
    await this.toInput.click();
    await this.page.getByRole("option", { name: `Bengaluru BLR` }).click();

    await this.dateInput.click();
    const day = String(new Date(date).getDate());

    const dayButton = this.page.getByRole("button", { name: day }).first();
    await dayButton.click();

    await this.searchButton.click();
  }

  async selectBus() {
    await this.page
      .getByLabel("Orange Tours")
      .getByRole("button", { name: "Select Seats" })
      .click();
  }

  async chooseSeat() {
    const availableSeat = this.page
      .locator(".seat.available[role='button']")
      .first();

    await expect(availableSeat).toBeVisible({ timeout: 10000 });
    await availableSeat.click();
    await expect(this.continueButton).toBeEnabled({ timeout: 10000 });
  }

  async continueToPassengerDetails() {
    await this.page
      .getByRole("button", { name: "Continue to passenger details" })
      .click();
  }
}
