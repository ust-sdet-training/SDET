import { Page } from '@playwright/test';
import type { FlightSearchCriteria } from '../types/booking';
import { dateAfterDays } from '../utils/dateUtils';

export class FlightSearchPage {
  constructor(private readonly page: Page) {}

  private readonly flightsLinkElement = () => this.page.getByRole('link', { name: /^flights$/i });
  private readonly fromBox = () => this.page.getByRole('combobox', { name: /^from/i });
  private readonly toBox = () => this.page.getByRole('combobox', { name: /^to/i });
  private readonly fromOption = (city: string) => this.page.getByRole('option').filter({ hasText: new RegExp(city, 'i') }).first();
  private readonly toOption = (city: string) => this.page.getByRole('option').filter({ hasText: new RegExp(city, 'i') }).first();
  private readonly departureField = () => this.page.getByRole('button', { name: /departure date/i }).first();
  private readonly nextMonthButton = () => this.page.getByRole('button', { name: /next month/i }).first();
  private readonly previousMonthButton = () => this.page.getByRole('button', { name: /previous month/i }).first();
  private readonly departureDayButton = (day: string) => this.page.getByRole('button', { name: new RegExp(`^${day}$`) }).first();
  private readonly cabinSelect = () => this.page.getByRole('combobox', { name: /cabin class/i }).first();
  private readonly searchButton = () => this.page.getByRole('button', { name: /search flights/i }).first();
  private readonly dateReadout = () => this.page.getByText(/selected/i).first();
  private readonly dateInput = () => this.page.locator('input[type="hidden"]').first();

  flightsLink() {
    return this.flightsLinkElement();
  }

  async open(): Promise<void> {
    await this.flightsLinkElement().click();
  }

  async selectRoute(origin: string, destination: string): Promise<void> {
    await this.fromBox().fill(origin);
    await this.fromOption(origin).click();
    await this.toBox().fill(destination);
    await this.toOption(destination).click();
  }

  async selectDepartureDate(daysToTravel: number): Promise<void> {
    const travelDate = dateAfterDays(daysToTravel);
    const targetMonth = `${travelDate.toLocaleString('en-US', { month: 'long' })} ${travelDate.getFullYear()}`;
    const travelDay = String(travelDate.getDate());

    await this.departureField().click().catch(() => undefined);

    for (let attempt = 0; attempt < 12; attempt += 1) {
      const calendarTitle = await this.page.locator('#cal-month').textContent().catch(() => '');
      if (calendarTitle?.trim() === targetMonth) {
        break;
      }

      await this.nextMonthButton().click();
    }

    await this.departureDayButton(travelDay).click().catch(() => undefined);
  }

  async selectDateByLabel(label: string): Promise<void> {
    await this.departureField().click().catch(() => undefined);
    await this.page.getByRole('button', { name: label }).click().catch(() => undefined);
  }

  async selectCabin(cabinClass: string): Promise<void> {
    const cabinSelect = this.cabinSelect();
    await cabinSelect.waitFor({ state: 'visible' }).catch(() => undefined);
    await cabinSelect.selectOption(cabinClass).catch(() => undefined);
  }

  async search(): Promise<void> {
    const searchButton = this.searchButton();
    await searchButton.waitFor({ state: 'visible' }).catch(() => undefined);
    await searchButton.click().catch(() => undefined);
  }

  selectedDateReadout() {
    return this.dateReadout();
  }

  dateInputValue() {
    return this.dateInput();
  }

  async searchFlights(criteria: FlightSearchCriteria): Promise<void> {
    await this.selectRoute(criteria.origin, criteria.destination);
    await this.selectDepartureDate(criteria.daysToTravel);
    await this.selectCabin(criteria.cabinClass);
    await this.search();
  }
}
