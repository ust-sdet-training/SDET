import { Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../src/logger";

export class FlightResultsPage extends BasePage {
  constructor(page: Page, log: AppLogger) {
    super(page, log);
  }

  readonly departureButton = this.page.getByRole("button", {
    name: "Departure",
  });

  async verifyRoute(routeHeading: string) {
    this.log.info(`Verifying route heading: ${routeHeading}`);
    await this.verifyVisible(this.departureButton);
    await this.verifyVisible(this.page.getByRole("heading", { name: routeHeading }));
  }

  async bookFlight(flightLabel: string) {
    this.log.info(`Booking flight: ${flightLabel}`);
    const flightRow = this.page.getByLabel(flightLabel);
    await this.verifyVisible(flightRow.getByRole("button", { name: "Book" }));
    await this.click(this.page.getByText(flightLabel));
    await this.click(flightRow.getByRole("button", { name: "Book" }));
  }
}