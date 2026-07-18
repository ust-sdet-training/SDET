import { test, expect } from '@playwright/test';

import { LoginPage } from '../pages/LoginPage';
import { HomePage } from '../pages/HomePage';
import { SearchResultPage } from '../pages/SearchResultPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { BookingConfirmationPage } from '../pages/BookingConfirmationPage';
import { MyTripsPage } from '../pages/MyTripsPage';

import { LoginFlow } from '../flows/LoginFlow';
import { BookingFlow } from '../flows/BookingFlow';

import { employee } from '../utils/testData';

test.describe('Round Trip Booking', () => {

    test('Verify user can complete a round trip booking', async ({ page }) => {

        const loginFlow = new LoginFlow(
            new LoginPage(page)
        );

        const bookingFlow = new BookingFlow(
            new HomePage(page),
            new SearchResultPage(page),
            new PassengerPage(page),
            new PaymentPage(page),
            new BookingConfirmationPage(page),
            new MyTripsPage(page)
        );

        await loginFlow.login(
            employee.email,
            employee.password
        );

        await bookingFlow.completeRoundTripBooking();

        await expect(
            page.getByRole('heading', {
                name: 'My Trips'
            })
        ).toBeVisible();

    });

});