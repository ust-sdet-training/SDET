import { test as base } from '@playwright/test';
import { LoginPage } from '../pages/auth/LoginPage';
import { BusSearchPage } from '../pages/bus/BusSearchPage';
import { SeatMapPage } from '../pages/bus/SeatMapPage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';

type Pages = {
    loginPage: LoginPage;
    busSearchPage: BusSearchPage;
    seatMapPage: SeatMapPage;
    passengerPage: PassengerPage;
    paymentPage: PaymentPage;
};

export const test = base.extend<Pages>({
    loginPage: async ({ page }, use) => { await use(new LoginPage(page)); },
    busSearchPage: async ({ page }, use) => { await use(new BusSearchPage(page)); },
    seatMapPage: async ({ page }, use) => { await use(new SeatMapPage(page)); },
    passengerPage: async ({ page }, use) => { await use(new PassengerPage(page)); },
    paymentPage: async ({ page }, use) => { await use(new PaymentPage(page)); },
});

export { expect } from '@playwright/test';