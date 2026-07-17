import { test, expect } from '../fixtures/baseFixture';

test.describe('TripStack Flight Booking', () => {

    test('Book Flight Successfully', async ({

        loginFlow,
        bookingFlow,
        myTripsFlow

    }) => {

        // Login
        await loginFlow.login();

        // Booking
        const pnr = await bookingFlow.bookFlight();

        // Verify Booking
        await myTripsFlow.verifyBooking(pnr);

        // Cancel Booking
        await myTripsFlow.cancelBooking(pnr);

    });

});