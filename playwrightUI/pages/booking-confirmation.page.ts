import { expect, type Page } from '@playwright/test';

function escapeRegExp(value: string): string {
  return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

export class BookingConfirmationPage {
  constructor(private readonly page: Page) {}

  async pnrFor(employeeId: string): Promise<string> {
    const pattern = new RegExp(`TS-${escapeRegExp(employeeId)}-\\d+`);
    const confirmationBody = this.page.locator('body');

    await expect(confirmationBody).toContainText(pattern, { timeout: 20_000 });
    const content = await confirmationBody.innerText();
    const match = content.match(pattern);

    if (!match?.[0]) {
      throw new Error(`Could not find a booking PNR matching ${pattern} on the confirmation page.`);
    }

    return match[0].trim();
  }
}
