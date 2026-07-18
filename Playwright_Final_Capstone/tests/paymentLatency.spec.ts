import { expect, test } from '../fixtures/testFixtures';
import type { Page } from '@playwright/test';
import { config } from '../utils/config';

test.describe('Payment latency detection', () => {
  test('detects injected payment latency during checkout and validates payment API behavior', async ({
    page,
    appPages,
    bookingData,
    user,
    passengerData,
    paymentData,
  }, testInfo) => {
    const consoleErrors: string[] = [];
    page.on('console', (msg) => {
      if (msg.type() === 'error') consoleErrors.push(msg.text());
    });

    await appPages.login.open();
    await appPages.login.login(user.email, user.password);

    await appPages.flightSearch.open();
    await appPages.flightSearch.searchFlights(bookingData);

    await expect(appPages.flightResults.flight(bookingData.flightName)).toBeVisible();

    await appPages.flightResults.bookFlight(bookingData.flightName);

    const selectedSeat = await appPages.seatSelection.selectFirstAvailableSeat();
    await appPages.seatSelection.continueToPassengerDetails();

    await appPages.passenger.fillDetails(passengerData, selectedSeat.seat);
    await appPages.passenger.continueToPayment();

    const paymentResponsePromise = page.waitForResponse((resp) => resp.url().includes('/book/payment') && resp.request().method() === 'POST', { timeout: config.paymentMaxMs + 5000 });

    const paymentStart = Date.now();
    await appPages.payment.pay(paymentData);

    const paymentResponse = await paymentResponsePromise;
    const paymentEnd = Date.now();
    const paymentLatencyMs = paymentEnd - paymentStart;

    await testInfo.attach('payment-response.json', {
      body: JSON.stringify({ url: paymentResponse.url(), status: paymentResponse.status(), ok: paymentResponse.ok(), timingMs: paymentLatencyMs }, null, 2),
      contentType: 'application/json',
    });

    const status = paymentResponse.status();
    expect([200, 201, 302]).toContain(status);

    expect(status).toBeLessThan(500);

    expect(paymentLatencyMs, `Payment API latency ${paymentLatencyMs}ms exceeded ${config.paymentMaxMs}ms`).toBeLessThanOrEqual(config.paymentMaxMs);

    await expect(appPages.confirmation.bookingReference()).toBeVisible({ timeout: config.paymentMaxMs + 5000 });

    const pnrText = (await appPages.confirmation.bookingReference().innerText()).trim();
    expect(pnrText).toMatch(/^TS-\d+-\d+$/);

    if (consoleErrors.length > 0) {
      await testInfo.attach('console-errors.txt', { body: consoleErrors.join('\n'), contentType: 'text/plain' });
    }
    expect(consoleErrors).toHaveLength(0);
  });
});
