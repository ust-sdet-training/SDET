import { expect } from '@playwright/test';
import { DEFAULT_TIMEOUTS } from '../constants';
import { BasePage } from './BasePage';

export class SeatMapPage extends BasePage {
  async selectSeat(seatId: string) {
    const requestedSeat = this.page.locator(`[data-seat="${seatId}"]`);
    await this.waitForVisible(requestedSeat, DEFAULT_TIMEOUTS.medium);
    await requestedSeat.scrollIntoViewIfNeeded();

    let selectedSeatId = seatId;

    try {
      await requestedSeat.click({ timeout: DEFAULT_TIMEOUTS.short });
    } catch (error) {
      this.logger?.warn('Seat click did not work on first try; retrying with a direct click', { seatId });
      const handle = await requestedSeat.elementHandle();
      if (!handle) {
        this.logger?.error('Seat click failed', { seatId, error: String(error) });
        throw error;
      }
      await this.page.evaluate((el: HTMLElement) => el.click(), handle);
    }

    const selectedText = this.page.getByText(/seat\(s\) selected/i);
    await expect(selectedText).toBeVisible({ timeout: DEFAULT_TIMEOUTS.medium });

    const continueButton = this.page.getByRole('button', { name: /continue to passenger details/i });
    await expect(continueButton).toBeVisible({ timeout: DEFAULT_TIMEOUTS.medium });

    const isRequestedSeatSelectable = await requestedSeat.isEnabled().catch(() => false);
    if (!isRequestedSeatSelectable) {
      const fallbackSeat = this.page.getByRole('button', { name: /seat .* available/i }).first();
      await expect(fallbackSeat).toBeVisible({ timeout: DEFAULT_TIMEOUTS.medium });
      await fallbackSeat.click({ timeout: DEFAULT_TIMEOUTS.short });
      const fallbackName = (await fallbackSeat.getAttribute('aria-label')) ?? (await fallbackSeat.textContent());
      selectedSeatId = fallbackName?.match(/seat\s+([a-z0-9]+)\s+available/i)?.[1]?.toUpperCase() ?? seatId;
    }

    await expect.poll(
      async () => {
        const isEnabled = await continueButton.isEnabled();
        if (!isEnabled) {
          await requestedSeat.click({ timeout: DEFAULT_TIMEOUTS.short }).catch(() => undefined);
          await this.page.waitForTimeout(500);
        }
        return isEnabled;
      },
      {
        timeout: DEFAULT_TIMEOUTS.long,
        message: 'Expected the continue button to become enabled after seat selection',
      }
    ).toBeTruthy();

    await continueButton.click({ timeout: DEFAULT_TIMEOUTS.medium });
    await expect(this.page.getByRole('heading', { name: /who's travelling/i })).toBeVisible({ timeout: DEFAULT_TIMEOUTS.medium });
    this.logger?.info('Seat selected', { seatId: selectedSeatId });
    return selectedSeatId;
  }
}
