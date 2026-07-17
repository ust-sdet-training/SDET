import { expect, type Page } from '@playwright/test';

export class BookingConfirmationPage {
  constructor(private readonly page: Page) {}

  async pnrFor(employeeId: string): Promise<string> {
    const pnr = this.page.getByText(new RegExp(`TS-${employeeId}-\\d+`));
    await expect(pnr).toBeVisible();
    return (await pnr.textContent())!.trim();
  }
}
