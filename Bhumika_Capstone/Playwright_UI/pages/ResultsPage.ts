import { Page } from "@playwright/test";
import { ResultsLocators } from "../locators/ResultsLocators";

export class ResultsPage {
  private locators: ResultsLocators;

  constructor(private page: Page) {
    this.locators = new ResultsLocators(page);
  }

  async verifyResultsPage() {
    await this.locators.heading().waitFor({ state: "visible" });
  }

  async selectFirstFlight() {
    await this.locators.firstBookButton().click();
  }
}
