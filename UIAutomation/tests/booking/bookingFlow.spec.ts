import { test, expect } from '@playwright/test';

import { LoginPage } from '../../pages/auth/LoginPage';
import { BusSearchPage } from '../../pages/bus/BusSearchPage';
import { SeatMapPage } from '../../pages/bus/SeatMapPage';

test(
    'TripStack Bus Booking Flow',
    async ({ page }) => {

        const loginPage =
            new LoginPage(page);

        const busSearchPage =
            new BusSearchPage(page);

        const seatMapPage =
            new SeatMapPage(page);

        await loginPage.open();

        await loginPage.login(
            'niaj@tripstack.test',
            'Password@123'
        );

        await expect(
            page.getByRole(
                'tab',
                {
                    name: 'Buses'
                }
            )
        ).toBeVisible();

        await busSearchPage.selectBusTab();

        await busSearchPage.selectFromCity(
            'Chandigarh IXC'
        );

        await busSearchPage.selectToCity(
            'Bengaluru BLR'
        );

        await busSearchPage.selectDate(
            '2026-07-31'
        );

        await busSearchPage.searchBus();

        await busSearchPage.openSeatSelection();

        await seatMapPage.selectSeat(
            'L3'
        );

        await seatMapPage.selectBoardingPoint();

        await seatMapPage.continueBooking();

        await page.screenshot({
            path:
                'reports/booking-success.png',
            fullPage: true
        });
    }
);