import { test, expect } from '@playwright/test';

import { LoginPage } from '../../pages/auth/LoginPage';
import { BusSearchPage } from '../../pages/bus/BusSearchPage';
import { SeatMapPage } from '../../pages/bus/SeatMapPage';
import { ENV } from '../../utils/env';
import { FROM_CITY_LABEL, TO_CITY_LABEL } from '../../utils/constants';
import { travelDate } from '../../utils/dateUtil';
import { resetNamespace } from '../../utils/apiReset';

test.beforeEach(async () => {
    await resetNamespace();
});

test(
    'Seat map remains usable under render changes @regression @resilience',
    async ({ page }) => {

        const loginPage = new LoginPage(page);
        const busSearchPage = new BusSearchPage(page);
        const seatMapPage = new SeatMapPage(page);

        await loginPage.open();
        await loginPage.login(ENV.EMAIL, ENV.PASSWORD);

        await busSearchPage.selectBusTab();
        await busSearchPage.selectFromCity(FROM_CITY_LABEL);
        await busSearchPage.selectToCity(TO_CITY_LABEL);
        await busSearchPage.selectDate(travelDate());
        await busSearchPage.searchBus();
        await busSearchPage.openSeatSelection();

        // Role + accessible-name based check — survives visual/DOM
        // render changes as long as the accessible name pattern holds.
        const availableCount = await seatMapPage.getAvailableSeatCount();
        expect(availableCount).toBeGreaterThan(0);

        // Prove we can actually still select a seat under whatever
        // render variant is currently active.
        const seatId = await seatMapPage.selectSeat();
        expect(seatId).toMatch(/^[LU]\d+$/); // L or U prefix per lower/upper deck

        console.log(`Seat map resilience check passed — selected seat ${seatId} out of ${availableCount} available.`);
    }
);