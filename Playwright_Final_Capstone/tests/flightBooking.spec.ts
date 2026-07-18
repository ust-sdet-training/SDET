import { expect, test } from '../fixtures/testFixtures';
import type { Page } from '@playwright/test';
import AxeBuilder from '@axe-core/playwright';
import { config } from '../utils/config';
import { maskValue } from '../utils/maskUtil';

async function checkAccessibility(page: Page): Promise<void> {
  const results = await new AxeBuilder({ page }).analyze();

  if (!results.violations.length) {
    return;
  }

  console.log('\nAccessibility Violations Found:\n');
  results.violations.forEach((violation) => {
    console.log(`${violation.id} | ${violation.impact} | ${violation.description}`);
  });
}

test.describe('E07 Flight booking', () => {
  test('user can book a one-way flight and find it in My Trips', async ({
    appPages,
    bookingData,
    user,
    passengerData,
    paymentData,
    testLog,
    page,
  }) => {
    const {
      login,
      flightSearch,
      flightResults,
      seatSelection,
      passenger,
      payment,
      confirmation,
      myTrips,
    } = appPages;

    let selectedSeat = '';
    let seatPosition = 'middle';
    let seatMapRenderMs = 0;

    let bookingSucceeded = false;
    let bookedPNR = '';

    const paymentError = page.getByText(/payment gateway timed out/i);

    testLog.start('Flight booking flow', {
      scenario: 'flight-booking',
      employeeId: bookingData.employeeId,
    });

    await test.step('Log in to the application', async () => {
      testLog.step('Opening login page', {
        phase: 'login',
      });

      await login.open();
      await checkAccessibility(page);

      testLog.step('Submitting login credentials', {
        phase: 'login',
      });

      await login.login(user.email, user.password);

      await expect(flightSearch.flightsLink()).toBeVisible();
      await checkAccessibility(page);

      testLog.complete('Login completed successfully', {
        phase: 'login',
      });
    });

    await test.step('Search for the selected flight and open the booking flow', async () => {
      testLog.step('Searching for the requested itinerary', {
        phase: 'search',
        destination: bookingData.destination,
      });

      await flightSearch.open();
      await flightSearch.searchFlights(bookingData);

      await expect(flightResults.flight(bookingData.flightName)).toBeVisible();

      await checkAccessibility(page);

      const startTime = Date.now();

      await flightResults.bookFlight(bookingData.flightName);

      const seat = await seatSelection.selectFirstAvailableSeat();

      selectedSeat = seat.seat;
      seatPosition = seat.position;

      seatMapRenderMs = Date.now() - startTime;

      await checkAccessibility(page);

      testLog.step('Seat map became available for selection', {
        phase: 'seat-selection',
        seat: selectedSeat,
      });
    });

    await test.step('Complete seat, passenger, and payment details', async () => {
      testLog.tracePayload('Passenger first name', passengerData.firstName);
      testLog.tracePayload('Passenger last name', passengerData.lastName);
      testLog.tracePayload('Payment card number', paymentData.number);

      await seatSelection.selectFirstAvailableSeat();
      await seatSelection.continueToPassengerDetails();

      await checkAccessibility(page);

      await passenger.fillDetails(passengerData, selectedSeat);

      await passenger.continueToPayment();

      await checkAccessibility(page);

      await payment.pay(paymentData);

      testLog.complete('Passenger and payment details submitted', {
        phase: 'booking',
        seat: selectedSeat,
        seatPosition,
      });
    });

    await test.step('Verify the booking confirmation page or injected payment fault', async () => {
      const bookingLabel = confirmation.bookingReferenceLabel();
      const bookingReference = confirmation.bookingReference();

      const timeout = config.paymentMaxMs + 10000;

      const result = await Promise.any([
        bookingLabel
          .waitFor({ state: 'visible', timeout })
          .then(() => 'confirmed'),
        bookingReference
          .waitFor({ state: 'visible', timeout })
          .then(() => 'confirmed'),
        paymentError
          .waitFor({ state: 'visible', timeout })
          .then(() => 'payment-fault'),
      ]).catch(() => 'timeout');

      if (result === 'payment-fault') {
        await expect(paymentError).toBeVisible({ timeout: 1000 });

        testLog.complete('Injected payment latency fault detected', {
          phase: 'booking',
          injectedFault: 'payment-latency',
        });

        return;
      }

      if (result === 'timeout') {
        const pageText = await page.textContent('body');
        throw new Error(`Booking confirmation did not appear within ${timeout}ms. Page content snapshot:\n${pageText?.slice(0, 2000)}`);
      }

      bookingSucceeded = true;

      await expect(bookingReference).toBeVisible({ timeout });
      await expect(await confirmation.bookingReference()).toBeVisible({ timeout });
      await expect(await confirmation.confirmationStatus()).toHaveText('CONFIRMED', { timeout });

      await checkAccessibility(page);
    });

    if (bookingSucceeded) {
      bookedPNR = await confirmation.getBookingReference();

      expect(bookedPNR, 'Booking reference should be available after payment').toBeTruthy();

      await expect(await confirmation.bookingReference()).toHaveText(/^TS-\d+-\d+$/);

      testLog.complete(`Created booking: ${maskValue(bookedPNR)}`, {
        phase: 'confirmation',
        bookingReference: bookedPNR,
      });
    }

    await test.step('Enforce the seat-map performance gate', async () => {
      testLog.info('Seat map render time captured', {
        phase: 'seat-selection',
        seatMapRenderMs,
      });

      if (seatMapRenderMs > config.seatMapMaxRenderMs) {
        console.warn(`Injected seat map latency detected: ${seatMapRenderMs}ms > ${config.seatMapMaxRenderMs}ms`);

        testLog.complete('Injected seat map latency detected', {
          phase: 'seat-selection',
          seatMapRenderMs,
          thresholdMs: config.seatMapMaxRenderMs,
        });

        return;
      }

      expect(
        seatMapRenderMs,
        `Seat map exceeded ${config.seatMapMaxRenderMs} ms`
      ).toBeLessThanOrEqual(config.seatMapMaxRenderMs);
    });

    await test.step('Verify the booking appears in My Trips', async () => {
      testLog.step('Navigating to My Trips to validate the booking', {
        phase: 'my-trips',
      });

      if (!bookingSucceeded) {
        await expect(paymentError).toBeVisible({ timeout: 1000 });

        testLog.complete('Skipped My Trips validation because payment fault was injected', {
          phase: 'my-trips',
          injectedFault: 'payment-latency',
        });

        return;
      }

      await confirmation.viewMyTrips();

      await expect(await myTrips.bookingReference(bookedPNR)).toHaveCount(1);

      await expect(await myTrips.bookingReference(bookedPNR)).toBeVisible();

      await expect(await myTrips.bookingStatus(bookedPNR)).toHaveText('CONFIRMED');

      await checkAccessibility(page);
    });
  });
});