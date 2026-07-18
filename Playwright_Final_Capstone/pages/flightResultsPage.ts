import { Page } from '@playwright/test';

export class FlightResultsPage {
  constructor(private readonly page: Page) {}

  private readonly flightCard = (flightName: string) =>
    this.page
      .locator('article, .flight-card, .result-card, tr, div')
      .filter({
        hasText: flightName,
        has: this.page.getByRole('button', { name: /book/i }),
      })
      .first();

  private readonly bookButton = (flightName: string) =>
    this.flightCard(flightName).getByRole('button', { name: /book/i }).first();

  flight(flightName: string) {
    return this.flightCard(flightName);
  }

  noFlightsHeading() {
    return this.page.getByRole('heading', { name: /no flights found/i });
  }

  noFlightsMessage() {
    return this.page.getByText(/nothing for/i);
  }

  async bookFlight(flightName: string): Promise<void> {
    await this.bookButton(flightName).click();
  }
}
