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

test.describe('Resilience Tests', () => {

    test('Verify booking succeeds after unavailable seat retry', async ({ page }) => {

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

        await bookingFlow.searchFlight();

        await bookingFlow.selectFlightAndSeat();

        await expect(
            page.getByRole('button', {
                name: 'Continue to payment'
            })
        ).toBeVisible();

    });

    test('Verify application handles checkout failure gracefully', async ({ page }) => {

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

        await bookingFlow.searchFlight();

        await bookingFlow.selectFlightAndSeat();

        await bookingFlow.enterPassengerDetails();

        await expect(
            page.getByRole('button', {
                name: /Pay/i
            })
        ).toBeVisible();

    });

});