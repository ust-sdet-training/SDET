import { test as base } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { FlightResultPage } from "../pages/FlightResultPage";
import { SeatPage } from "../pages/SeatPage";
import { PassengerPage } from "../pages/PassengerPage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingPage } from "../pages/BookingPage";

type TripStackFixtures = {
    loginPage: LoginPage;
    homePage: HomePage;
    flightResultPage: FlightResultPage;
    seatPage: SeatPage;
    passengerPage: PassengerPage;
    paymentPage: PaymentPage;
    bookingPage: BookingPage;
};

export const test = base.extend<TripStackFixtures>({

    loginPage: async ({ page }, use) => {
        await use(new LoginPage(page));
    },

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    flightResultPage: async ({ page }, use) => {
        await use(new FlightResultPage(page));
    },

    seatPage: async ({ page }, use) => {
        await use(new SeatPage(page));
    },

    passengerPage: async ({ page }, use) => {
        await use(new PassengerPage(page));
    },

    paymentPage: async ({ page }, use) => {
        await use(new PaymentPage(page));
    },

    bookingPage: async ({ page }, use) => {
        await use(new BookingPage(page));
    }

});

export { expect } from "@playwright/test";