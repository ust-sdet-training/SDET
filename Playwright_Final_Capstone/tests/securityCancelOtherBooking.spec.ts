import { expect, test } from '@playwright/test';
import { config } from '../utils/config';

test.describe('Booking cancellation security', () => {
  test('rejects cancellation of another employee booking with 403', async ({ page }) => {
    const cancellationUrl = new URL(`bookings/other-employee-${Date.now()}/cancel`, config.baseURL);

    const response = await page.request.post(cancellationUrl.toString(), {
      headers: { Accept: 'application/json', 'Content-Type': 'application/json' },
      data: {},
    });

    expect(
      response.status(),
      'Cancellation endpoint should reject cross-employee access',
    ).toBe(403);
  });
});
