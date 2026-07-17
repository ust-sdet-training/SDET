import { Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../src/logger";

export class FlightFilterPage extends BasePage {
  constructor(page: Page, log: AppLogger) {
    super(page, log);
  }

  readonly maxPriceSlider = this.page.getByRole("slider", {
    name: "Maximum price",
  });

  async filterByAirlines(airlines: string[]) {
    this.log.info(`Filtering by airlines: ${airlines.join(", ")}`);
    for (const airline of airlines) {
      await this.click(this.page.getByRole("checkbox", { name: airline }));
    }
  }

  async filterByTimeSlots(slots: string[]) {
    this.log.info(`Filtering by time slots: ${slots.join(", ")}`);
    for (const slot of slots) {
      await this.click(this.page.getByRole("checkbox", { name: slot }));
    }
  }

  async setMaxPrice(price: string) {
    this.log.info(`Setting maximum price: ${price}`);
    await this.fill(this.maxPriceSlider, price);
  }
}