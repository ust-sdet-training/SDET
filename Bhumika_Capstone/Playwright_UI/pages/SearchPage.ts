import { Page } from "@playwright/test";
import { SearchLocators } from "../locators/SearchLocator";
import { FlightData } from "../data/FlightData";
import { logger } from "../logger/Logger";

export class SearchPage {
  private locators: SearchLocators;

  constructor(private page: Page) {
    this.locators = new SearchLocators(page);
  }

  async openFlightsSearch() {
    await this.page.goto("/flights/search");
    logger.info("Opening flights search page");
  }

  async selectFrom(cityCode: string) {
    await this.locators.fromInput().fill(cityCode);
  }

  async selectTo(cityCode: string) {
    await this.locators.toInput().fill(cityCode);
  }

  private async selectDateByOffset(offsetDays: number, gridName: RegExp) {
    const targetDate = new Date();
    targetDate.setDate(targetDate.getDate() + offsetDays);
    const dayText = targetDate.getDate().toString();

    const grid = this.page.getByRole("grid", { name: gridName }).first();
    const count = await grid.count();

    if (count === 0) {
      return;
    }

    await grid
      .getByRole("button", { name: new RegExp(dayText, "i") })
      .first()
      .click();
  }

  async selectDepartureDate(offsetDays: number) {
    await this.selectDateByOffset(offsetDays, /departure date/i);
  }

  async selectReturnDate(offsetDays: number) {
    await this.selectDateByOffset(offsetDays, /return date/i);
  }

  async searchFlights() {
    await this.locators.searchButton().click();
    logger.info("Clicking search flights button");
  }

  async searchRoundTrip(config = FlightData.roundTrip) {
    await this.selectFrom(config.from);
    await this.selectTo(config.to);
    await this.selectDepartureDate(config.departureAfterDays);
    await this.selectReturnDate(config.returnAfterDays);
    await this.searchFlights();
  }
}
