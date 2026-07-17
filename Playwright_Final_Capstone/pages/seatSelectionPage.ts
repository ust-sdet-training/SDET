import { Locator, Page } from '@playwright/test';

export class SeatSelectionPage {
  constructor(private readonly page: Page) {}

  private getSeatButton(seat: string, position: string) {
    return this.page.getByLabel(`Seat ${seat}, ${position}, available`);
  }

  private getAnyAvailableSeatButton() {
    return this.page.getByLabel(/Seat\s+.+,\s+.+,\s+available/i).first();
  }

  private getContinueButton() {
    return this.page.getByRole('button', { name: 'Continue to passenger details' });
  }

  private async activateSeatButton(seatButton: Locator): Promise<void> {
    await seatButton.waitFor({ state: 'visible' });

    const seatLabel = (await seatButton.getAttribute('aria-label')) ?? '';
    const isAlreadySelected =
      seatLabel.toLowerCase().includes('selected') ||
      (await seatButton.getAttribute('aria-pressed')) === 'true' ||
      (await seatButton.getAttribute('data-selected')) === 'true';

    if (isAlreadySelected) {
      return;
    }

    try {
      await seatButton.click();
    } catch {
      await seatButton.click({ force: true });
    }
  }

  seat(seat: string, position = 'middle') {
    return this.getSeatButton(seat, position);
  }

  async selectFirstAvailableSeat(): Promise<{ seat: string; position: string }> {
    const seatButton = this.getAnyAvailableSeatButton();
    await this.activateSeatButton(seatButton);

    const seatLabel = (await seatButton.getAttribute('aria-label')) ?? '';
    const labelParts = seatLabel
      .split(',')
      .map((part) => part.trim())
      .filter(Boolean);

    if (labelParts.length < 2) {
      throw new Error(`Unable to read seat label: ${seatLabel}`);
    }

    const seat = labelParts[0].replace(/^Seat\s+/i, '').trim();
    const position = labelParts[1].toLowerCase();

    return { seat, position };
  }

  async selectSeat(seat: string, position = 'middle'): Promise<void> {
    await this.activateSeatButton(this.getSeatButton(seat, position));
  }

  async continueToPassengerDetails(): Promise<void> {
    await this.getContinueButton().click();
  }
}
