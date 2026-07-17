import { expect, test } from '../fixtures/testFixtures';
import type { Page } from '@playwright/test';
import AxeBuilder from '@axe-core/playwright';
import { config } from '../utils/config';
import { maskValue } from '../utils/maskUtil';

const checkAccessibility = async (page: Page): Promise<void> => {
  const results = await new AxeBuilder({ page }).analyze();

  if (results.violations.length > 0) {
    console.log('\nAccessibility Violations Found:\n');

    results.violations.forEach(violation => {
      console.log(
        `${violation.id} | ${violation.impact} | ${violation.description}`
      );
    });
  }
};



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

    testLog.start('Flight booking flow', {
      scenario: 'flight-booking',
      employeeId: bookingData.employeeId,
    });

    await test.step('Log in to the application', async () => {
      testLog.step('Opening login page', { phase: 'login' });

      await login.open();
      await checkAccessibility(page);

      testLog.step('Submitting login credentials', { phase: 'login' });

      await login.login(user.email, user.password);

      await expect(flightSearch.flightsLink()).toBeVisible();
      await checkAccessibility(page);

      testLog.complete('Login completed successfully', {
        phase: 'login',
      });
    });

    let selectedSeat = '';
    let seatPosition = 'middle';
    let seatMapRenderMs = 0;

    await test.step(
      'Search for the selected flight and open the booking flow',
      async () => {
        testLog.step('Searching for the requested itinerary', {
          phase: 'search',
          destination: bookingData.destination,
        });

        await flightSearch.open();
        await flightSearch.searchFlights(bookingData);

        await expect(
          flightResults.flight(bookingData.flightName),
        ).toBeVisible();

        await checkAccessibility(page);

        const startedAt = Date.now();

        await flightResults.bookFlight(bookingData.flightName);

        const selectedSeatDetails =
          await seatSelection.selectFirstAvailableSeat();

        selectedSeat = selectedSeatDetails.seat;
        seatPosition = selectedSeatDetails.position;

        await checkAccessibility(page);

        testLog.step('Seat map became available for selection', {
          phase: 'seat-selection',
          seat: selectedSeat,
        });

        seatMapRenderMs = Date.now() - startedAt;
      },
    );

    await test.step(
      'Complete seat, passenger, and payment details',
      async () => {
        testLog.tracePayload(
          'Passenger first name',
          passengerData.firstName,
        );

        testLog.tracePayload(
          'Passenger last name',
          passengerData.lastName,
        );

        testLog.tracePayload(
          'Payment card number',
          paymentData.number,
        );

        await seatSelection.selectFirstAvailableSeat();
        await seatSelection.continueToPassengerDetails();

        await checkAccessibility(page);

        await passenger.fillDetails(
          passengerData,
          selectedSeat,
        );

        await passenger.continueToPayment();

        await checkAccessibility(page);

        await payment.pay(paymentData);

        testLog.complete(
          'Passenger and payment details submitted',
          {
            phase: 'booking',
            seat: selectedSeat,
            seatPosition,
          },
        );
      },
    );

    await test.step(
      'Verify the booking confirmation page',
      async () => {
        await expect(
          await confirmation.bookingReferenceLabel(),
        ).toBeVisible();

        await expect(
          await confirmation.bookingReference(),
        ).toBeVisible();

        await expect(
          await confirmation.confirmationStatus(),
        ).toHaveText('CONFIRMED');

        await checkAccessibility(page);
      },
    );

    const bookedPNR =
      await confirmation.getBookingReference();

    expect(
      bookedPNR,
      'Booking reference should be available after payment',
    ).toBeTruthy();

    await expect(
      await confirmation.bookingReference(),
    ).toHaveText(/^TS-\d+-\d+$/);

    testLog.complete(
      `Created booking: ${maskValue(bookedPNR)}`,
      {
        phase: 'confirmation',
        bookingReference: bookedPNR,
      },
    );

    await test.step(
      'Enforce the seat-map performance gate',
      async () => {
        testLog.info(
          'Seat map render time captured',
          {
            phase: 'seat-selection',
            seatMapRenderMs,
          },
        );

        expect(
          seatMapRenderMs,
          `Seat map exceeded ${config.seatMapMaxRenderMs} ms`,
        ).toBeLessThanOrEqual(
          config.seatMapMaxRenderMs,
        );
      },
    );

    await test.step(
      'Verify the booking appears in My Trips',
      async () => {
        testLog.step(
          'Navigating to My Trips to validate the booking',
          { phase: 'my-trips' },
        );

        await confirmation.viewMyTrips();

        await expect(
          await myTrips.bookingReference(bookedPNR),
        ).toHaveCount(1);

        await expect(
          await myTrips.bookingReference(bookedPNR),
        ).toBeVisible();

        await expect(
          await myTrips.bookingStatus(bookedPNR),
        ).toHaveText('CONFIRMED');

        await checkAccessibility(page);
      },
    );
  });
});