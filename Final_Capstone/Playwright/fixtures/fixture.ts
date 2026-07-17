import { test as base } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { FlightSearchPage } from "../pages/FlightSearchPage";
import { BookingPage } from "../pages/BookingPage";
import { CabinPage } from "../pages/CabinPage";
import { DetailsPage } from "../pages/DetailsPage";
import { CardPage } from "../pages/CardPage";
import {ConfirmedPage } from "../pages/ConfirmedPage";

type PageFixtures = {

    loginPage: LoginPage;

    flightSearchPage: FlightSearchPage;

    bookingPage: BookingPage;

    cabinPage: CabinPage;

    detailsPage: DetailsPage;

    paymentPage: CardPage;

    bookingConfirmationPage: ConfirmedPage;

};

export const test = base.extend<PageFixtures>({

    loginPage: async ({ page }, use) => {

        await use(new LoginPage(page));

    },

    flightSearchPage: async ({ page }, use) => {

        await use(new FlightSearchPage(page));

    },

    bookingPage: async ({ page }, use) => {

        await use(new BookingPage(page));

    },

    cabinPage: async ({ page }, use) => {

        await use(new CabinPage(page));

    },

    detailsPage: async ({ page }, use) => {

        await use(new DetailsPage(page));

    },

    paymentPage: async ({ page }, use) => {

        await use(new CardPage(page));

    },

    bookingConfirmationPage: async ({ page }, use) => {

        await use(new ConfirmedPage(page));

    }

});

export { expect } from "@playwright/test";