import { test as base } from "@playwright/test";

import { logger } from "../logger/Logger";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { BusSearchResultsPage } from "../pages/BusSearchResultsPage";
import { SeatSelectionPage } from "../pages/SeatSelectionPage";
import { PassengerDetailsPage } from "../pages/PassengerDetailsPage";
import { PaymentPage } from "../pages/PaymentPage";
import { BookingConfirmationPage } from "../pages/BookingConfirmationPage";
import { MyTripsPage } from "../pages/MyTripsPage";

import { BusBookingFlow } from "../flows/BusBookingFlow";

type Fixtures = {

    log: typeof logger;

    loginPage: LoginPage;
    homePage: HomePage;
    busSearchResultsPage: BusSearchResultsPage;
    seatSelectionPage: SeatSelectionPage;
    passengerDetailsPage: PassengerDetailsPage;
    paymentPage: PaymentPage;
    bookingConfirmationPage: BookingConfirmationPage;
    myTripsPage: MyTripsPage;

    busBookingFlow: BusBookingFlow;
};

export const test = base.extend<Fixtures>({

    log: async ({}, use) => {
        await use(logger);
    },

    loginPage: async ({ page }, use) => {
        await use(new LoginPage(page));
    },

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    busSearchResultsPage: async ({ page }, use) => {
        await use(new BusSearchResultsPage(page));
    },

    seatSelectionPage: async ({ page }, use) => {
        await use(new SeatSelectionPage(page));
    },

    passengerDetailsPage: async ({ page }, use) => {
        await use(new PassengerDetailsPage(page));
    },

    paymentPage: async ({ page }, use) => {
        await use(new PaymentPage(page));
    },

    bookingConfirmationPage: async ({ page }, use) => {
        await use(new BookingConfirmationPage(page));
    },

    myTripsPage: async ({ page }, use) => {
        await use(new MyTripsPage(page));
    },

    busBookingFlow: async (
        {
            loginPage,
            homePage,
            busSearchResultsPage,
            seatSelectionPage,
            passengerDetailsPage,
            paymentPage,
            bookingConfirmationPage,
            myTripsPage
        },
        use
    ) => {

        await use(
            new BusBookingFlow(
                loginPage,
                homePage,
                busSearchResultsPage,
                seatSelectionPage,
                passengerDetailsPage,
                paymentPage,
                bookingConfirmationPage,
                myTripsPage
            )
        );
    }

});

export { expect } from "@playwright/test";