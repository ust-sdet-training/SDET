import { test, expect } from '../fixtures/base.fixture';

import { employee } from '../utils/testData';

test.describe('Payment Feature', () => {

    test('Verify user can complete payment successfully', async ({
        loginFlow,
        bookingFlow,
        page
    }) => {

        // Login
        await loginFlow.login(
            employee.email,
            employee.password
        );

        // Search Flight
        await bookingFlow.searchFlight();

        // Flight Selection
        await bookingFlow.selectFlightAndSeat();

        // Passenger Details
        await bookingFlow.enterPassengerDetails();

        // Payment
        await bookingFlow.makePayment();

        // Booking Success
        await bookingFlow.verifyBookingSuccess();

        // Assertions
        await expect(
            page.getByRole('heading', {
                name: "You're all set! 🎉"
            })
        ).toBeVisible();

        await expect(
            page.getByText('Booking reference (PNR)')
        ).toBeVisible();

        await expect(
            page.getByText('Journey')
        ).toBeVisible();

        await expect(
            page.getByText('Seats',{exact:true})
        ).toBeVisible();

        await expect(
            page.getByText('Amount paid', { exact: true })
        ).toBeVisible();

        await expect(
            page.getByRole('button', {
                name: 'View my trips'
            })
        ).toBeEnabled();

    });

});