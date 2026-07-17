import { expect, test } from '../fixtures/testFixtures';
import { config } from '../utils/config';
import { logger } from '../utils/logger';
import { maskValue } from '../utils/maskUtil';

test.describe('E07 Flight booking', () => {
  test('user can book a one-way flight and find it in My Trips', async ({
    appPages,
    bookingData,
    user,
    passengerData,
    paymentData,
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

    logger.start('flight booking flow', { scenario: 'flight-booking', employeeId: bookingData.employeeId });

    await test.step('Log in to the application', async () => {
      logger.step('Opening login page', { phase: 'login' });
      await login.open();
      logger.step('Submitting login credentials', { phase: 'login' });
      await login.login(user.email, user.password);
      await expect(flightSearch.flightsLink()).toBeVisible();
      logger.complete('Login completed successfully', { phase: 'login' });
    });

    let selectedSeat = '';
    let seatPosition = 'middle';
    let seatMapRenderMs = 0;

    await test.step('Search for the selected flight and open the booking flow', async () => {
      logger.step('Searching for the requested itinerary', { phase: 'search', destination: bookingData.destination });
      await flightSearch.open();
      await flightSearch.searchFlights(bookingData);
      await expect(flightResults.flight(bookingData.flightName)).toBeVisible();

      const startedAt = Date.now();
      await flightResults.bookFlight(bookingData.flightName);

      const selectedSeatDetails = await seatSelection.selectFirstAvailableSeat();
      selectedSeat = selectedSeatDetails.seat;
      seatPosition = selectedSeatDetails.position;

      logger.step('Seat map became available for selection', { phase: 'seat-selection', seat: selectedSeat });
      seatMapRenderMs = Date.now() - startedAt;
    });

    await test.step('Complete seat, passenger, and payment details', async () => {
      logger.tracePayload('Passenger first name', passengerData.firstName);
      logger.tracePayload('Passenger last name', passengerData.lastName);
      logger.tracePayload('Payment card number', paymentData.number);

      await seatSelection.selectFirstAvailableSeat();
      await seatSelection.continueToPassengerDetails();
      await passenger.fillDetails(passengerData, selectedSeat);
      await passenger.continueToPayment();
      await payment.pay(paymentData);
      logger.complete('Passenger and payment details submitted', { phase: 'booking', seat: selectedSeat, seatPosition });
    });

    await test.step('Verify the booking confirmation page', async () => {
      await expect(await confirmation.bookingReferenceLabel()).toBeVisible();
      await expect(await confirmation.bookingReference()).toBeVisible();
      await expect(await confirmation.confirmationStatus()).toHaveText('CONFIRMED');
    });

    const bookedPNR = await confirmation.getBookingReference();
    expect(bookedPNR, 'Booking reference should be available after payment').toBeTruthy();
    await expect(await confirmation.bookingReference()).toHaveText(
      new RegExp(`^TS-${bookingData.employeeId}-\\d+$`),
    );
    logger.complete(`Created booking: ${maskValue(bookedPNR)}`, { phase: 'confirmation', bookingReference: bookedPNR });

    await test.step('Enforce the seat-map performance gate', async () => {
      logger.info(`Seat map rendered in ${seatMapRenderMs} ms`);
      expect(seatMapRenderMs, `Seat map exceeded ${config.seatMapMaxRenderMs} ms`).toBeLessThanOrEqual(
        config.seatMapMaxRenderMs,
      );
    });

    await test.step('Verify the booking appears in My Trips', async () => {
      logger.step('Navigating to My Trips to validate the booking', { phase: 'my-trips' });
      await confirmation.viewMyTrips();
      await expect(await myTrips.bookingReference(bookedPNR)).toHaveCount(1);
      await expect(await myTrips.bookingReference(bookedPNR)).toBeVisible();
      await expect(await myTrips.bookingStatus(bookedPNR)).toHaveText('CONFIRMED');
    });
  });
});
