import { test, expect } from '../fixtures/base.fixture';

import { employee } from '../utils/testData';

test.describe('My Trips Feature', () => {

    test('Verify booking is available in My Trips', async ({
        loginFlow,
        bookingFlow,
        page
    }) => {

        await loginFlow.login(
            employee.email,
            employee.password
        );

        await bookingFlow.completeBooking();

        await expect(
            page.getByRole('heading', {
                name: 'My Trips'
            })
        ).toBeVisible();

        await expect(
            page.getByText('CONFIRMED')
        ).toBeVisible();

        await expect(
            page.getByRole('button', {
                name: 'Cancel'
            })
        ).toBeVisible();

    });

});