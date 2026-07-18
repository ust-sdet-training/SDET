import { test as base } from "@playwright/test";

import { BusPage } from "../pages/BusPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { MyTripsPage } from "../pages/MyTripsPage";
import { LoginPage } from "../pages/LoginaPage";

export const test = base.extend({

    login: async ({ page }, use) => {

        await use(new LoginPage(page));
    },

    bus: async ({ page }, use) => {

        await use(new BusPage(page));
    },

    passenger: async ({ page }, use) => {

        await use(new PassengerPage(page));
    },

    payment: async ({ page }, use) => {

        await use(new PaymentPage(page));
    },

    myTrips: async ({ page }, use) => {

        await use(new MyTripsPage(page));
    }

});

export { expect } from "@playwright/test";