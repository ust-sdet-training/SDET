import { Page, TestInfo } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../src/logger";

export class HomePage extends BasePage {
  constructor(page: Page, log: AppLogger) {
    super(page, log);
  }

  readonly flightsTab = this.page.getByRole("tab", { name: "Flights" });

  readonly fromBox = this.page.getByRole("combobox", { name: "From" });

  readonly toBox = this.page.getByRole("combobox", { name: "To" });

  readonly dateBox = this.page.getByRole("textbox", { name: "Date" });

  readonly searchButton = this.page.getByRole("button", { name: "Search" });

  async open(baseUrl: string) {
    this.log.info("Opening Flight Home Page");
    await this.goto(baseUrl);
  }

  async verifyHomePage() {
    this.log.info("Verifying Flight Home Page");
    await this.verifyVisible(this.flightsTab);
  }

  async selectFrom(city: string, optionLabel: string) {
    this.log.info(`Selecting source city: ${city}`);
    await this.click(this.fromBox);
    await this.fill(this.fromBox, city);
    await this.click(this.page.getByRole("option", { name: optionLabel }));
  }

  async selectTo(city: string, optionLabel: string) {
    this.log.info(`Selecting destination city: ${city}`);
    await this.click(this.toBox);
    await this.fill(this.toBox, city);
    await this.click(this.page.getByRole("option", { name: optionLabel }));
  }

  async selectDate(date: string) {
    this.log.info(`Selecting travel date: ${date}`);
    await this.fill(this.dateBox, date);
  }

  async searchFlights() {
    this.log.info("Clicking Search Flights");
    await this.click(this.searchButton);
  }

  async capture(testInfo: TestInfo) {
    this.log.info("Capturing Flight Home Page screenshot");
    await this.takeScreenshot(testInfo, "Flight Home Page");
  }
}